package controllers.admin

import com.google.inject.Inject
import com.sksamuel.scrimage.Image
import com.sksamuel.scrimage.nio.JpegWriter
import play.api.libs.iteratee.Enumerator
import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import reactivemongo.api.gridfs.ReadFile
import play.modules.reactivemongo.{ReactiveMongoApi, JSONFileToSave, MongoController, ReactiveMongoComponents}
import play.api.libs.json.{Json, JsString, JsObject}
import play.modules.reactivemongo.json._
import play.api.libs.json.Json._

class PictureController @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends Controller with MongoController with ReactiveMongoComponents {

  import MongoController.readFileReads

  val gridFS = reactiveMongoApi.gridFS
  val fsParser = gridFSBodyParser(gridFS)
  type JSONReadFile = ReadFile[JSONSerializationPack.type, JsString]

  def save = Action.async(parse.multipartFormData) { request =>
    request.body.file("file") match {
      case Some(photo) =>
        val resizedFile = Image.fromFile(photo.ref.file).cover(600, 400).forWriter(JpegWriter())
        val enumerator = Enumerator.fromStream(resizedFile.stream)
        val fileToSave = JSONFileToSave(photo.filename, photo.contentType)
        gridFS.save(enumerator, fileToSave).map {
          case file => Created(Json.obj("id" -> file.id))

        }.recover {
          case e => InternalServerError("Failed to upload picture")
        }
    }
  }

  def get(id: String) = Action.async { request =>
    val image = gridFS.find[JsObject, JSONReadFile](Json.obj("_id" -> id))
    serve[JsString, JSONReadFile](gridFS)(image)
  }

  def getAllIDs = Action.async { request =>
    gridFS.find[JsObject, JSONReadFile](Json.obj()).collect[List]().map { files =>
      Ok(toJson(files.map(_.id)))
    }
  }
}

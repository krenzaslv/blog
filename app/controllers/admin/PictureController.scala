package controllers.admin


import javax.inject.Inject
import com.sksamuel.scrimage.Image
import db.picture.PictureDao
import db.post.PostService
import play.api.libs.iteratee.Enumerator
import scala.concurrent.Future
import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{JsValue, Json, JsObject, JsString}
import reactivemongo.api.gridfs.{DefaultFileToSave, ReadFile}
import play.modules.reactivemongo.json._
import reactivemongo.api.gridfs.Implicits._
import play.modules.reactivemongo.{
MongoController, ReactiveMongoApi, ReactiveMongoComponents
}

class PictureController @Inject()(val reactiveMongoApi: ReactiveMongoApi, val postService: PostService) extends Controller with MongoController with ReactiveMongoComponents {

  import MongoController.readFileReads

  val gridFS = reactiveMongoApi.gridFS
  val fsParser = gridFSBodyParser(gridFS)
  type JSONReadFile = ReadFile[JSONSerializationPack.type, JsString]

  def upload = Action.async(fsParser) { request =>
    val futureFile: Future[ReadFile[JSONSerializationPack.type, JsValue]] = request.body.files.head.ref

    futureFile.map { file =>
      Created
    }.recover {
      case e: Throwable => InternalServerError(e.getMessage)
    }
  }

  def save = Action.async(parse.multipartFormData) { request =>
    request.body.file("file") match {
      case Some(photo) =>
        val fileToSave = DefaultFileToSave(photo.filename, photo.contentType)
        val resizedFile = Image.fromFile(photo.ref.file).resizeToWidth(120)
        val enumerator = Enumerator.fromStream(resizedFile.stream)
        PictureDao.save(enumerator, fileToSave).map {
          case file => Ok

        }.recover {
          case e => InternalServerError("Failed to upload picture")
        }
    }
  }

  def get(id: String) = Action.async { request =>
    val file = gridFS.find[JsObject, JSONReadFile](Json.obj("_id" -> id))
    request.getQueryString("inline") match {
      case Some("true") =>
        serve[JsString, JSONReadFile](gridFS)(file, CONTENT_DISPOSITION_INLINE)

      case _ => serve[JsString, JSONReadFile](gridFS)(file)
    }
  }
}

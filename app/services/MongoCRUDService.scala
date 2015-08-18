package services

import reactivemongo.api.ReadPreference

import scala.concurrent.Future
import play.api.libs.json._

abstract class MongoCRUDService[E: Format] extends CRUDService[E] {

  import play.api.libs.concurrent.Execution.Implicits.defaultContext
  import play.modules.reactivemongo.json.collection.JSONCollection
  import play.modules.reactivemongo.json._

  val DEFAULT_MAX = 100

  def collection: JSONCollection

  override def findAll: Future[List[E]] = collection.
    find(Json.obj()).
    cursor[E](readPreference = ReadPreference.primary).
    collect[List](DEFAULT_MAX)


  override def findById(id: String): Future[Option[E]] = collection.
    find(Json.obj("$oid" -> id)).
    one[E]

  override def create(entity: E): Future[JsResult[E]] = {
    val doc = Json.toJson(entity).as[JsObject]
    collection.insert(doc).map {
      case result if result.ok => JsSuccess(entity)
      case result => JsError(result.message)
    }
  }

  override def delete(id: String): Future[JsResult[String]] = {
    collection.remove(Json.obj("$oid" -> id)).map {
      case result if result.ok => JsSuccess(result.message)
      case result => JsError(result.errmsg.toString)
    }
  }

  override def update(id: String, entity: E): Future[JsResult[E]] = {
    val doc = Json.toJson(entity).as[JsObject]
    collection.update(Json.obj("$oid" -> id), doc).map {
      case result if result.ok => JsSuccess(entity)
      case result => JsError(result.message)
    }
  }
}

package core.util

import core.BaseModel
import play.api.libs.json._
import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json._
import reactivemongo.play.json._

trait MongoDbHelpers[T <: BaseModel] {

  def findByIdQuery(id: BSONObjectID): JsObject = Json.obj("_id" -> id)

  def addToSetQuery(field: String, entity: T)(implicit writes: Writes[T]): JsObject = Json.obj("$addToSet" -> Json.obj(field -> entity))

}

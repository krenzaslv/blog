package core

import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.modules.reactivemongo.ReactiveMongoApi

//Don't change this. Fucking implicit conversions of Reactivemongo
import play.api.libs.json._
import reactivemongo.play.json._, collection._
import play.modules.reactivemongo._


abstract class BaseRepository[T <: BaseModel](implicit format: OFormat[T]) {

  val collectionName: String
  val reactiveMongoApi: ReactiveMongoApi

  def collection: Future[JSONCollection] = reactiveMongoApi.database.map { db =>
    db.collection[JSONCollection](collectionName)
  }

  def add(entity: T): Future[WriteResult] = collection.flatMap(_.insert(entity))

  def find(id: BSONObjectID): Future[Option[T]] = collection.flatMap {
    _.find(Json.obj("_id" -> Json.toJson(id))).one[T]
  }

  def findAll: Future[List[T]] = collection.flatMap {
    _.find(Json.obj()).cursor[T]().collect[List]()
  }

  def find(criteria: JsObject): Future[List[T]] = collection.flatMap {
    _.find(criteria).cursor[T]().collect[List]()
  }

  def update(entity: T) = ??? //collection.flatMap {_.findAndUpdate(Json.obj("_id" -> Json.toJson(entity._id)), entity)}

  def remove(id: BSONObjectID): Future[WriteResult] = collection.flatMap {
    _.remove(Json.obj("_id" -> Json.toJson(id)))
  }
}

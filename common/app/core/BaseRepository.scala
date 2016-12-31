package core

import core.util.MongoDbHelpers
import reactivemongo.api.commands.{UpdateWriteResult, WriteResult}
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.modules.reactivemongo.ReactiveMongoApi
import play.api.libs.json._
//Don't change this. Fucking implicit conversions of Reactivemongo
import reactivemongo.play.json._, collection._
import play.modules.reactivemongo._
import collection.JSONBatchCommands.FindAndModifyCommand.FindAndModifyResult


abstract class BaseRepository[T <: BaseModel](implicit format: OFormat[T]) extends MongoDbHelpers[T]{

  val collectionName: String
  val reactiveMongoApi: ReactiveMongoApi


  def collection: Future[JSONCollection] = reactiveMongoApi.database.map { db =>
    db.collection[JSONCollection](collectionName)
  }

  def add(entity: T): Future[Boolean] = collection.flatMap(_.insert(entity).map(_.ok))

  def find(id: BSONObjectID): Future[Option[T]] = collection.flatMap {
    _.find(findByIdQuery(id)).one[T]
  }

  def findAll: Future[List[T]] = collection.flatMap {
    _.find(Json.obj()).cursor[T]().collect[List]()
  }

  def find(criteria: JsObject): Future[List[T]] = collection.flatMap {
    _.find(criteria).cursor[T]().collect[List]()
  }

  //TODO: return updated model
  def update(entity: T): Future[Boolean] = collection.flatMap {
    _.update(findByIdQuery(entity._id), entity).map(_.ok)
  }

  def modify(id: BSONObjectID, updateModifier: JsObject): Future[Boolean] = collection.flatMap { col =>
    col.update(findByIdQuery(id), updateModifier).map(_.ok
    )
  }

  def remove(id: BSONObjectID): Future[Boolean] = collection.flatMap {
    _.remove(findByIdQuery(id)).map(_.ok)
  }
}

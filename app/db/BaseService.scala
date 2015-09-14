package db


import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONObjectID
import reactivemongo.extensions.json.dao.JsonDao
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

abstract class BaseService[Model](dao: JsonDao[Model, BSONObjectID]) extends Service[Model] {

  def save(entity: Model) = {
    dao.insert(entity)
  }

  override def find(id: BSONObjectID): Future[Option[Model]] = dao.findById(id)

  override def findAll: Future[List[Model]] = dao.findAll()

  override def delete(id: BSONObjectID): Future[WriteResult] = dao.removeById(id)

  def update(id: BSONObjectID, entity: Model) = ???
}

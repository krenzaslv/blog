package core

import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Future

trait BaseRepository[T <: BaseModel] {

  def add(entity: T): Future[WriteResult] = ???

  def find(id: BSONObjectID): Future[Option[T]] = ???

  def findAll: Future[List[T]] = ???

  def remove(id: BSONObjectID): Future[WriteResult] = ???
}

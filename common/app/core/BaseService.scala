package core

import reactivemongo.api.commands.{UpdateWriteResult, WriteResult}
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Future

trait BaseService[T <: BaseModel] {

  val repository: BaseRepository[T]

  def save(entity: T): Future[WriteResult] = repository.add(entity)

  def get(id: BSONObjectID): Future[Option[T]] = repository.find(id)

  def getAll: Future[List[T]] = repository.findAll

  def delete(id: BSONObjectID): Future[WriteResult] = repository.remove(id)

  def update(entity: T): Future[UpdateWriteResult] = repository.update(entity)
}

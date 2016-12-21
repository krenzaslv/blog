package db

import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Future

trait BaseService[T <: BaseModel] { repository : BaseRepository[T] =>

  def save(entity: T): Future[WriteResult] = repository.save(entity)

  def find(id: BSONObjectID): Future[Option[T]] =repository.find(id)

  def findAll: Future[List[T]] = repository.findAll

  def delete(id: BSONObjectID): Future[WriteResult] = repository.delete(id)
}

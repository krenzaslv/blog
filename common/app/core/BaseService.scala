package core

import reactivemongo.api.commands.{UpdateWriteResult, WriteResult}
import reactivemongo.bson.BSONObjectID
import scala.concurrent.Future

trait BaseService[T <: BaseModel] {

  val repository: BaseRepository[T]

  def save(entity: T): Future[WriteResult] = repository.add(entity)

  def find(id: BSONObjectID): Future[Option[T]] = repository.find(id)

  def findAll: Future[List[T]] = repository.findAll

  def delete(id: BSONObjectID): Future[WriteResult] = repository.remove(id)

  def update(entity: T): Future[UpdateWriteResult] = repository.update(entity)

//  def findById(id: BSONObjectID): Future[Option[T]] = repository.find(Json.obj("_id" -> Json.toJson(id))).map(_.headOption)
}

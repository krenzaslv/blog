package db

import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Future

trait Service[E]{

  def save(entity: E) : Future[WriteResult]

  def find(id: BSONObjectID): Future[Option[E]]

  def findAll: Future[List[E]]

  def delete(id: BSONObjectID) : Future[WriteResult]
}

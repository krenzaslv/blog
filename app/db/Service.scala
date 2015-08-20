package db

import java.util.UUID

import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

trait Service[E]{

  def save(entity: E): Future[E]

  def find(id: UUID): Future[Option[E]]

  def findAll: Future[List[E]]

  def delete(id: UUID) : Future[WriteResult]
}

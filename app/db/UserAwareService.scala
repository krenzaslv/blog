package db

import java.util.UUID

import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

trait UserAwareService[E] {

  def saveById(id: UUID, entity: E): Future[E]

  def deleteById(id: UUID, entityID: UUID): Future[WriteResult]

  def findById(id: UUID): Future[List[E]]
}

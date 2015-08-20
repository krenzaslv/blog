package db

import java.util.UUID

import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

trait UserAwareService[E] {

  def save(entity: E, userID: UUID): Future[E]

  def delete(id: UUID, userID: UUID): Future[WriteResult]
}

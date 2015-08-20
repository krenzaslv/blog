package db

import java.util.UUID

import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

trait UserAwareService[E] {

  def delete(id: UUID, userID: UUID): Future[WriteResult]

  def find(id: UUID, userID: UUID): Future[Option[E]]

  def findAllByUserId(userID: UUID): Future[List[E]]

}

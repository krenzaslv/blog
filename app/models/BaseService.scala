package models

import java.util.UUID

import com.mohiva.play.silhouette.api.services.IdentityService
import models.user.User
import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

abstract class BaseService[E](dao: BaseDao[E]) extends IdentityService[User] with Service[E]{

  override def find(id: UUID): Future[Option[E]] = dao.find(id)

  override def findAll : Future[List[E]] = dao.findAll

  override def delete(id: UUID) : Future[WriteResult] = dao.delete(id)
}

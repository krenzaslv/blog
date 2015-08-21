package db

import java.util.UUID

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService
import db.user.{UserDao, User}
import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

abstract class BaseService[E](dao: BaseDao[E]) extends Service[E]{

  override def find(id: UUID): Future[Option[E]] = dao.find(id)

  override def findAll: Future[List[E]] = dao.findAll

  override def delete(id: UUID): Future[WriteResult] = dao.delete(id)

}

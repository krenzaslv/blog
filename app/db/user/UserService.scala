package db.user

import java.util.UUID

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService
import db.BaseService
import play.api.libs.concurrent.Execution.Implicits._
import reactivemongo.api.commands.WriteResult
import scala.concurrent.Future

class UserService @Inject()(userDao: UserDao) extends BaseService[User](userDao) with IdentityService[User] {

  override def save(newUser: User): Future[User] = {
    userDao.find(newUser.userID).flatMap {
      case Some(user) =>
        userDao.update(user.copy(
          firstName = newUser.firstName,
          lastName = newUser.lastName,
          email = newUser.email
        ))
      case None =>
        userDao.save(User(
          userID = UUID.randomUUID(),
          loginInfo = newUser.loginInfo,
          firstName = newUser.firstName,
          lastName = newUser.lastName,
          email = newUser.email
        ))
    }
  }

  override def retrieve(loginInfo: LoginInfo): Future[Option[User]] = userDao.find(loginInfo)
}

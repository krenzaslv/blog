package db.user

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService
import db.BaseService
import play.api.libs.json.Json
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class UserService extends BaseService[User](UserDao) with IdentityService[User] {

  override def retrieve(loginInfo: LoginInfo): Future[Option[User]] = UserDao.findOne(Json.obj("loginInfo" -> loginInfo))
}

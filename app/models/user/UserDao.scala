package models.user

import com.google.inject.ImplementedBy
import com.mohiva.play.silhouette.api.LoginInfo
import models.BaseDao
import scala.concurrent.Future

@ImplementedBy(classOf[UserDaoMongo])
trait UserDao extends BaseDao[User]{

  val TABLE_NAME = "users"

  def find(loginInfo: LoginInfo) : Future[Option[User]]
}

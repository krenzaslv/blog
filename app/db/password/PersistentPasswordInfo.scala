package db.password

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.util.PasswordInfo

case class PersistentPasswordInfo(
                                   loginInfo: LoginInfo,
                                   authInfo: PasswordInfo
                                   )

object PersistentPasswordInfo{

  import play.api.libs.json._

  implicit val passwordInfoFormat = Json.format[PasswordInfo]
  implicit val persistentPasswordInfoFormat = Json.format[PersistentPasswordInfo]
}
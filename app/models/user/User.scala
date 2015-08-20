package models.user

import java.util.UUID

import com.mohiva.play.silhouette.api.{LoginInfo, Identity}

case class User(
                 userID: UUID,
                 loginInfo: LoginInfo,
                 firstName: Option[String],
                 lastName: Option[String],
                 email: Option[String]
                 ) extends Identity

object User {

  import play.api.libs.json._

  implicit val jsonFormat = Json.format[User]
}


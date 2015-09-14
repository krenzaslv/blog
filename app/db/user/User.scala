package db.user

import com.mohiva.play.silhouette.api.{LoginInfo, Identity}
import reactivemongo.bson.BSONObjectID
import play.modules.reactivemongo.json.BSONFormats._

case class User(
                 _id: BSONObjectID = BSONObjectID.generate,
                 loginInfo: LoginInfo,
                 firstName: Option[String],
                 lastName: Option[String],
                 email: Option[String]) extends Identity

object User {

  import play.api.libs.json._

  implicit val userFormat = Json.format[User]
}


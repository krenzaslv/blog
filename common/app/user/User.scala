package user

import com.mohiva.play.silhouette.api.{Identity, LoginInfo}
import core.BaseModel
import reactivemongo.bson.BSONObjectID

case class User( _id: BSONObjectID = BSONObjectID.generate,
                 loginInfo: LoginInfo,
                 firstName: Option[String],
                 lastName: Option[String],
                 email: Option[String]) extends Identity with BaseModel

object User {

  import play.api.libs.json._
  import play.modules.reactivemongo.json._

  implicit val userFormat = Json.format[User]
}


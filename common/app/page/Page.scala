package page

import core.BaseModel
import reactivemongo.bson.BSONObjectID

case class Page(
                 override val _id: BSONObjectID = BSONObjectID.generate(),
                 name: String,
                 content: String
               ) extends BaseModel

object Page {


  import play.api.libs.json._
  import reactivemongo.play.json._


  implicit val pageFormat = Json.format[Page]
}

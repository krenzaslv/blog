package menu


import core.BaseModel
import reactivemongo.bson.BSONObjectID

case class Menu(
                 override val _id: BSONObjectID = BSONObjectID.generate,
                 title: String,
                 children: List[Menu] = List.empty,
                 uri: String = "http://www.google.ch"
               ) extends BaseModel

object Menu {

  import play.api.libs.json._
  import reactivemongo.play.json._

  implicit val menuFormat = Json.format[Menu]
}
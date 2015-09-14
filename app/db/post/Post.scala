package db.post

import reactivemongo.bson.BSONObjectID
import play.modules.reactivemongo.json.BSONFormats._


case class Post(
                 _id: BSONObjectID = BSONObjectID.generate,
                 title: String,
                 content: String
                 )

object Post {

  import play.api.libs.json._
  import play.api.libs.functional.syntax._

  //  implicit val postFormat = Json.format[Post]

  implicit val postFormat =
    ((__ \ "_id").formatNullable[BSONObjectID].inmap[BSONObjectID](_.getOrElse(BSONObjectID.generate), Some(_)) ~
      (__ \ "title").format[String] ~
      (__ \ "content").format[String])(Post.apply, unlift(Post.unapply))
}


package db.post

import db.BaseModel
import reactivemongo.bson.BSONObjectID
import play.modules.reactivemongo.json.BSONFormats._


case class Post(
                 _id: BSONObjectID = BSONObjectID.generate,
                 headerImageID: Option[BSONObjectID],
                 images: Option[List[BSONObjectID]],
                 title: String,
                 content: String,
                 tags: Option[List[String]]
                 ) extends BaseModel

object Post {

  import play.api.libs.json._
  import play.api.libs.functional.syntax._

  implicit val postFormat =
    ((__ \ "_id").formatNullable[BSONObjectID].inmap[BSONObjectID](_.getOrElse(BSONObjectID.generate), Some(_)) ~
      (__ \ "headerImageID").formatNullable[BSONObjectID] ~
      (__ \ "images").formatNullable[List[BSONObjectID]] ~
      (__ \ "title").format[String] ~
      (__ \ "content").format[String] ~
      (__ \ "tags").formatNullable[List[String]]
      )(Post.apply, unlift(Post.unapply))
}


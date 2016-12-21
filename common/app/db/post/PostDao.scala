package db.post

import db.MongoContext
import reactivemongo.bson.BSONObjectID
import reactivemongo.extensions.json.dao.JsonDao
import scala.concurrent.ExecutionContext.Implicits.global
import Post._
import play.modules.reactivemongo.json.BSONFormats._


object PostDao extends JsonDao[Post, BSONObjectID] (MongoContext.db, "post")

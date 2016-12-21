package db.user

import db.MongoContext
import reactivemongo.bson.BSONObjectID
import reactivemongo.extensions.json.dao.JsonDao
import scala.concurrent.ExecutionContext.Implicits.global
import play.modules.reactivemongo.json._

object UserDao extends JsonDao[User, BSONObjectID] (MongoContext.db, "user")
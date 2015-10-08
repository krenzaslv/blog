package db.picture

import db.MongoContext
import reactivemongo.bson.BSONObjectID
import reactivemongo.extensions.dao.JsonFileDao

object PictureDao extends JsonFileDao[BSONObjectID](MongoContext.db, "picture")

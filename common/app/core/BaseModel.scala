package core

import reactivemongo.bson.BSONObjectID

trait BaseModel {
  val _id: BSONObjectID
}

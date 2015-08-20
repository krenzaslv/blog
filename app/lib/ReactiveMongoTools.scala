package lib

import reactivemongo.bson._

object ReactiveMongoTools {

  def BSONObjectIDtoString: String = {
    val objectId = BSONObjectID.generate
    objectId.stringify
  }
}
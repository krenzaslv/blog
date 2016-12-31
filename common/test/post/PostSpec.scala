package post

import org.scalatest.{Matchers, WordSpec}
import reactivemongo.bson.BSONObjectID

class PostSpec extends WordSpec with Matchers {

  "A post class" when {

    "created" should {
      "generate ID if not provided" in {
        val post = Post(headerImageID = None, images = None, title = "Title", content = "Content", tags = None)

        post._id.stringify should not be empty
      }

      "take id if provided" in {
        val id = BSONObjectID.generate
        val post = Post(id, None, None, "Title", "Content", None)

        post._id should be(id)
      }
    }
  }
}
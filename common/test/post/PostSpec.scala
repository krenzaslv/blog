package post

import org.scalatest.{Matchers, WordSpec}
import reactivemongo.bson.BSONObjectID

class PostSpec extends WordSpec with Matchers {

  "A post class" when {
    "read from json" should {
      "fail with no content" is (pending)
      "fail with no title" is (pending)
      "be parsed with optional headerimage, tags, images" is (pending)
    }

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
package user

import com.mohiva.play.silhouette.api.LoginInfo
import org.scalatest.{Matchers, MustMatchers, WordSpec}
import reactivemongo.bson.BSONObjectID


class UserSpec extends WordSpec with Matchers {

  "A user" when {

    "created" should {
      "generate ID if not provided" in {
        val user = User(firstName = None, lastName = None, email = None, loginInfo = LoginInfo("provider", "key"))

        user._id.stringify should not be empty
      }

      "take id if provided" in {
        val id = BSONObjectID.generate
        val user = User(id, LoginInfo("provider", "key"), None, None, None)

        user._id should be(id)
      }
    }
  }
}

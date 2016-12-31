package user

import com.mohiva.play.silhouette.api.LoginInfo
import org.scalatest.time.{Seconds, Span}
import play.api.libs.json.Json
import testUtil.{BaseRepositorySpec}

import scala.concurrent.Await

class UserRepositorySpec extends BaseRepositorySpec[User, UserRepository] {

  override val repo: UserRepository = app.injector.instanceOf[UserRepository]

  def newUser = User(loginInfo = LoginInfo("provider", "key"), firstName = Some("Krenza"), lastName = Some("Solve"), email = None)

  "A UserRepository" when {
    "inserting a new user" should {
      "return successful" in {
        val addeUser = newUser

        val add = repo.add(addeUser)

        whenReady(add) { result =>
          result mustBe true
        }
      }

      "not add a user twice" ignore {
        val addedUser = newUser
        val add = repo.add(addedUser)
        Await.result(add, Span(1, Seconds))

        val doubleAdd = repo.add(addedUser)

        whenReady(doubleAdd) { result =>
          result mustBe false
        }
      }

      "find a user by id" in {
        val addedUser = newUser
        val add = repo.add(addedUser)
        Await.result(add, Span(1, Seconds))

        val user = repo.find(addedUser._id)

        whenReady(user) { result =>
          result.value mustEqual addedUser
        }
      }
    }

    "deleting a user" should {
      "return a successful WriteResult" in {
        val user = insertUser(newUser)

        val delete = repo.remove(user._id)

        whenReady(delete) {
          _ mustBe true
        }
      }

      "not find the user anymore" in {
        val user = insertUser(newUser)
        val delete = repo.remove(user._id)
        Await.result(delete, Span(1, Seconds))

        val find = repo.find(user._id)

        whenReady(find) {
          _ mustBe empty
        }
      }
    }

    "listing users" should {
      "find every user" in {
        insertUser(newUser)
        insertUser(newUser)

        val findAll = repo.findAll

        whenReady(findAll) {
          _ must have size 2
        }
      }
    }

    "searching for users" should {
      "only return matching users" in {
        insertUser(newUser)
        insertUser(newUser)
        insertUser(newUser.copy(firstName = Some("special")))

        val find = repo.find(Json.obj("firstName" -> newUser.firstName.value))

        whenReady(find) {
          _ must have size 2
        }
      }
    }

    "updating a user" should {
      "have new values when found" in {
        val user = insertUser(newUser)
        val update = repo.update(user.copy(firstName = Some("awesome"), email = Some("halli@hallo.com")))
        Await.result(update, Span(2, Seconds))

        val find = repo.find(user._id)

        whenReady(find) { user =>
          user.value.firstName mustBe Some("awesome")
          user.value.email mustEqual (Some("halli@hallo.com"))
        }
      }
    }
  }

  def insertUser(user: User): User = {
    val writeResult = repo.add(user)
    Await.result(writeResult, Span(2, Seconds))
    user
  }
}
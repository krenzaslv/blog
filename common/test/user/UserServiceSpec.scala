package user

import com.mohiva.play.silhouette.api.LoginInfo
import org.mockito.Mockito.{times, verify}
import org.scalatest.WordSpec
import org.scalatest.mock.MockitoSugar
import play.api.libs.json.Json
import reactivemongo.bson.BSONObjectID

class UserServiceSpec extends WordSpec with MockitoSugar {

  val mockUserRepository = mock[UserRepository]
  val mockUser = User(BSONObjectID.generate, LoginInfo("provider", "key"), None, None, None)
  val userService = new UserService(mockUserRepository)

  "A UserService" when {
    "called delete" should {
      val id = BSONObjectID.generate()
      userService.delete(id)

      "execute remove on UserRepository" in {
        verify(mockUserRepository, times(1)).remove(id)
      }
    }

    "called save" should {
      userService.save(mockUser)

      "execute add on UserRepository" in {
        verify(mockUserRepository, times(1)).add(mockUser)
      }
    }

    "called getAll()" should {
      userService.findAll

      "execute findAll on UserRepository" in {
        verify(mockUserRepository, times(1)).findAll
      }
    }

    "called get" should {
      val id = BSONObjectID.generate
      userService.find(id)

      "execute find on UserRepository" in {
        verify(mockUserRepository, times(1)).find(id)
      }
    }
  }
}


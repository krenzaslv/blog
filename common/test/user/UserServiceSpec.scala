package user

import com.mohiva.play.silhouette.api.LoginInfo
import org.mockito.Mockito.{times, verify}
import org.scalatest.WordSpec
import org.scalatest.mock.MockitoSugar
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
      userService.getAll

      "execute findAll on UserRepository" in {
        verify(mockUserRepository, times(1)).findAll
      }
    }

    "called get" should {
      val id = BSONObjectID.generate
      userService.get(id)

      "execute find on UserRepository" in {
        verify(mockUserRepository, times(1)).find(id)
      }
    }

    "call update" should {
      userService.update(mockUser)

      "execute update on PostRepository " in {
        verify(mockUserRepository, times(1)).update(mockUser)
      }
    }
  }
}


package post

import org.mockito.Mockito._
import org.scalatest.WordSpec
import org.scalatest.mock.MockitoSugar
import reactivemongo.bson.BSONObjectID

class PostServiceSpec extends WordSpec with MockitoSugar {

  val mockPostRepository = mock[PostRepository]
  val mockPost = Post(BSONObjectID.generate(), None, None, "Title", "Content", None)
  val postService = new PostService(mockPostRepository)

  "A PostService" when {
    "called delete" should {
      val id = BSONObjectID.generate()
      postService.delete(id)

      "execute remove on PostRepository" in {
        verify(mockPostRepository, times(1)).remove(id)
      }
    }

    "called save" should {
      postService.save(mockPost)

      "execute add on PostRepository" in {
        verify(mockPostRepository, times(1)).add(mockPost)
      }
    }

    "called getAll()" should {
      postService.findAll

      "execute findAll on PostRepository" in {
        verify(mockPostRepository, times(1)).findAll
      }
    }

    "called get" should {
      val id = BSONObjectID.generate
      postService.find(id)

      "execute find on PostRepository" in {
        verify(mockPostRepository, times(1)).find(id)
      }
    }

    "call update" should{
      postService.update(mockPost)

      "execute update on PostRepository " in{
        verify(mockPostRepository, times(1)).update(mockPost)
      }
    }
  }
}

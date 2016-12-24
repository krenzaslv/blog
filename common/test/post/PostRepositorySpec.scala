package post

import org.scalatest.time.{Seconds, Span}
import play.api.libs.json.Json
import testUtil.BaseRepositoryTest

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await

class PostRepositorySpec extends BaseRepositoryTest[Post, PostRepository] {

  override val repo: PostRepository = app.injector.instanceOf[PostRepository]

  def newPost = Post(title = "title1", content = "content1", headerImageID = None, images = None, tags = None)

  "A post" when {
    "inserted" should {

      "return successful" in {
        val r = repo.add(newPost)
        whenReady(r) { result =>
          result.ok mustBe true
        }
      }

      "fail if already exists" in {}

      "be found by Id" in {
        val p = insertPost(newPost)

        val post = repo.find(p._id)
        whenReady(post) { result =>
          result.value mustEqual p
        }
      }
    }

    "deleted" should {
      "return successful WriteResult" in {
        val post = insertPost(newPost)

        val result = repo.remove(post._id)
        whenReady(result) {
          _.ok mustBe true
        }
      }

      "not be found anymore" in {
        val post = insertPost(newPost)
        val delete = repo.remove(post._id)
        Await.result(delete, Span(2, Seconds))

        val find = repo.find(post._id)
        whenReady(find) {
          _.mustBe(None)
        }
      }
    }
  }

  "A PostController" when {
    "called findAll" should {

      "return every post" in {
        insertPost(newPost)
        insertPost(newPost)
        val findAll = repo.findAll
        whenReady(findAll) {
          _.size mustBe 2
        }
      }
    }

    "called with criteria" should {
      "only return matching posts" in {
        insertPost(newPost)
        insertPost(newPost)
        insertPost(newPost.copy(title = "special"))

        val find = repo.find(Json.obj("title" -> newPost.title))
        whenReady(find) { posts =>
          println(posts.toString())
          posts.size mustBe 2
        }
      }
    }
  }

  def insertPost(post: Post): Post = {
    val writeResult = repo.add(post)
    Await.result(writeResult, Span(2, Seconds))
    post
  }
}
package post

import org.scalatest.time.{Seconds, Span}
import play.api.libs.json.Json
import reactivemongo.api.commands.LastError
import testUtil.BaseRepositorySpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await

class PostRepositorySpec extends BaseRepositorySpec[Post, PostRepository] {

  override val repo: PostRepository = app.injector.instanceOf[PostRepository]

  def newPost = Post(title = "title1", content = "content1", headerImageID = None, images = None, tags = None)

  "A PostRepository" when {
    "inserting a new post" should {
      "return successful" in {
        val addedPost = newPost

        val add = repo.add(addedPost)

        whenReady(add) { result =>
          result mustBe 'ok
        }
      }

      "not insert a post twice" ignore {
        val addedPost = newPost
        val add = repo.add(addedPost)
        Await.result(add, Span(1, Seconds))

        val doubleAdd = repo.add(addedPost)

        whenReady(doubleAdd) { result =>
          result must not be 'ok
        }
      }

      "find a post by id" in {
        val addedPost = newPost
        val add = repo.add(addedPost)
        Await.result(add, Span(1, Seconds))

        val post = repo.find(addedPost._id)

        whenReady(post) { result =>
          result.value mustEqual addedPost
        }
      }
    }

    "deleting a post" should {
      "return a successful WriteResult" in {
        val post = insertPost(newPost)

        val delete = repo.remove(post._id)

        whenReady(delete) {
          _ mustBe 'ok
        }
      }

      "not find the post anymore" in {
        val post = insertPost(newPost)
        val delete = repo.remove(post._id)
        Await.result(delete, Span(1, Seconds))

        val find = repo.find(post._id)

        whenReady(find) {
          _ mustBe empty
        }
      }
    }

    "listing posts" should {
      "find every post" in {
        insertPost(newPost)
        insertPost(newPost)

        val findAll = repo.findAll

        whenReady(findAll) {
          _ must have size 2
        }
      }
    }

    "searching for posts" should {
      "only return matching posts" in {
        insertPost(newPost)
        insertPost(newPost)
        insertPost(newPost.copy(title = "special"))

        val find = repo.find(Json.obj("title" -> newPost.title))

        whenReady(find) { posts =>
          posts must have size 2
        }
      }
    }

    "updating a post" should {
      "have new values when found" in {
        val post = insertPost(newPost)
        val update = repo.update(post.copy(title = "awesome", tags = Some(List("awesometag"))))
        Await.result(update, Span(2, Seconds))

        val find = repo.find(post._id)

        whenReady(find) { post =>
          post.value.title mustBe "awesome"
          post.value.tags mustEqual (Some(List("awesometag")))
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
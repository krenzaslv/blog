package controllers


import admin.controllers.PostAdminController
import org.mockito.Mockito
import org.scalatest.mock.MockitoSugar
import post.{Post, PostService}
import testUtil.BaseControllerSpec
import org.mockito.Mockito._
import org.scalatest.BeforeAndAfter
import play.api.mvc.Result
import play.api.test.FakeRequest
import play.api.test.Helpers._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import reactivemongo.api.commands.{DefaultWriteResult, WriteResult}
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Future

class PostAdminControllerSpec extends BaseControllerSpec with MockitoSugar with BeforeAndAfter {

  val mockService = mock[PostService]
  val controller = new PostAdminController(mockService)

  before(Mockito.reset(mockService))

  def newPost = Post(headerImageID = None, images = None, title = "title", content = "content", tags = None)

  "A PostController Reads[Post]" when {

    "called findAll" should {
      "responds with status 200" in {
        when(mockService.findAll) thenReturn Future(List(newPost, newPost))

        val result: Future[Result] = controller.list.apply(FakeRequest())

        verify(mockService, times(1)).findAll
        status(result) mustBe 200
      }

      "serves response with post" in {
        when(mockService.findAll) thenReturn Future(List(newPost, newPost))

        val result: Future[Result] = controller.list.apply(FakeRequest())

        verify(mockService, times(1)).findAll
        contentAsString(result) must include("title")
      }
    }
  }

  "called find(id)" should {
    "serves response with post" in {
      val id = BSONObjectID.generate()
      when(mockService.find(id)) thenReturn Future(Some(newPost.copy(_id = id)))

      val result: Future[Result] = controller.get(id.stringify).apply(FakeRequest())

      verify(mockService, times(1)).find(id)
      contentAsString(result) must include(id.stringify)
    }

    "responds with status 400" in {
      val id = "nope"
      val result: Future[Result] = controller.get(id).apply(FakeRequest())
      status(result) mustBe 400
    }

    "responds with status 404" in {
      val id = BSONObjectID.generate()
      when(mockService.find(id)) thenReturn Future(None)

      val result: Future[Result] = controller.get(id.stringify).apply(FakeRequest())

      verify(mockService, times(1)).find(id)
      status(result) mustBe 404
    }
  }

  "A PostController Writes[Post]" when {
    "creating a post" should {
      "respond 201 when created" is (pending)

      "respond 400 with invalid json" in {}
      "respond 500 when not created" is (pending)
    }
    "updating a post" should {
      "respond 204 when updated" is (pending)
      "respond 400 with invalid json" is (pending)
      "respond 500 when not updated" is (pending)
    }
  }
}

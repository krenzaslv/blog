package menu

import org.scalatest.{BeforeAndAfter, MustMatchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import page.{Page, PageService}
import org.mockito.Mockito._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Seconds, Span}
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future


class MenuServiceSpec extends WordSpec with MockitoSugar with BeforeAndAfter with ScalaFutures with MustMatchers {

  val mockMenuRepository = mock[MenuRepository]
  val mockPageService = mock[PageService]
  val menuService = new MenuService(mockMenuRepository, mockPageService)

  val page = Page(name = "pageName", content = "content")
  val menu = Menu(title = "title", pageId = page._id)

  "A MenuService" should {
    "not add a Menu" when {
      "the corresponding page is not found" in {
        when(mockPageService.find(page._id)) thenReturn Future(None)

        val save = menuService.save(menu)

        whenReady(save) {
          _ mustBe false
        }
      }
    }

    "add a Menu" when {
      "the corresponding page is found" in {
        when(mockPageService.find(page._id)) thenReturn Future(Some(page))
        when(mockMenuRepository.add(menu)) thenReturn Future(true)

        val save = menuService.save(menu)

        whenReady(save) {
          _ mustBe true
        }
      }
    }

    after(reset(mockMenuRepository, mockPageService))
  }
}

package menu

import org.scalatest.time.{Seconds, Span}
import play.api.libs.json.Json
import reactivemongo.api.commands.GroupAggregation
import reactivemongo.bson.BSONObjectID
import reactivemongo.core.commands.AddToSet
import testUtil.BaseRepositorySpec

import scala.concurrent.Await

class MenuRepositorySpec extends BaseRepositorySpec[Menu, MenuRepository] {

  override val repo: MenuRepository = app.injector.instanceOf[MenuRepository]

  def newMenu = Menu(title = "title", pageId = BSONObjectID.generate())

  "A MenuRepository" should {
    "return successful" when {
      "adding new Menu" in {
        val add = repo.add(newMenu)

        whenReady(add) { result =>
          result mustBe true
        }
      }


      "updating a menu with child" in {
        val menu = newMenu
        val add = repo.add(menu)
        Await.result(add, Span(1, Seconds))
        val child = newMenu
        val updatedMenu = menu.copy(children = List(child))
        val update = repo.update(menu)
        whenReady(update) { result =>
          result mustBe true
        }
      }
    }

    "update with child and" when {
      "return new child when found" in {
        val menu = newMenu
        val add = repo.add(menu)
        Await.result(add, Span(1, Seconds))
        val child = newMenu
        val updatedMenu = menu.copy(children = List(child))
        val update = repo.update(updatedMenu)
        Await.result(update, Span(1, Seconds))
        val find = repo.find(menu._id)
        whenReady(find) {
          _.value.children must contain(child)
        }
      }

      "return new child when modified" in {
        val menu = newMenu
        val add = repo.add(menu)
        Await.result(add, Span(1, Seconds))
        val child = newMenu
        val update = repo.addChildMenu(menu._id, child)
        Await.result(update, Span(1, Seconds))
        val find = repo.find(menu._id)
        whenReady(find) {
          _.value.children must contain(child)
        }
      }
    }
  }
}



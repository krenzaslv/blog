package menu

import com.google.inject.Inject
import core.BaseService
import page.PageService
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

class MenuService @Inject()(val repository: MenuRepository, val pageService: PageService) extends BaseService[Menu] {

  override def save(entity: Menu): Future[Boolean] = {
    pageService.find(entity.pageId) flatMap {
      case Some(_) => super.save(entity)
      case None => Future(false)
    }
  }
}

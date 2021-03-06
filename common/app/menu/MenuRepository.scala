package menu

import com.google.inject.{Inject, Singleton}
import core.BaseRepository
import core.util.MongoDbHelpers
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.UpdateWriteResult
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Future

@Singleton
class MenuRepository @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends BaseRepository[Menu] with MongoDbHelpers[Menu] {

  override val collectionName: String = "menu"

  def addChildMenu(parentId: BSONObjectID, menu: Menu): Future[Boolean] = modify(parentId, addToSetQuery("children", menu))
}

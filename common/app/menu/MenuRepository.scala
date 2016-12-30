package menu

import com.google.inject.{Inject, Singleton}
import core.BaseRepository
import core.util.MongoDbHelpers
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.bson.BSONObjectID

@Singleton
class MenuRepository @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends BaseRepository[Menu] with MongoDbHelpers[Menu] {

  override val collectionName: String = "menu"

  def addChildMenu(parentId: BSONObjectID, menu: Menu) = modify(parentId, addToSetQuery("children", menu))
}

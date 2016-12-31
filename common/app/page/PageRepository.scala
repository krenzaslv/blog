package page

import com.google.inject.{Inject, Singleton}
import core.BaseRepository
import play.modules.reactivemongo.ReactiveMongoApi

@Singleton
class PageRepository @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends BaseRepository[Page] {

  override val collectionName: String = "page"
}

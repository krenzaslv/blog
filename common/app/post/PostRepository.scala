package post

import com.google.inject.{Inject, Singleton}
import core.BaseRepository
import play.modules.reactivemongo.ReactiveMongoApi

@Singleton
class PostRepository @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends BaseRepository[Post]{
  override val collectionName = "post"
}
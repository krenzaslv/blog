package user

import com.google.inject.{Inject, Singleton}
import core.BaseRepository
import play.modules.reactivemongo.ReactiveMongoApi

@Singleton
class UserRepository @Inject()(val reactiveMongoApi: ReactiveMongoApi) extends BaseRepository[User] {
  override val collectionName = "user"
}
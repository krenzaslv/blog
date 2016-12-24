package user

import com.google.inject.{Inject, Singleton}
import core.BaseRepository
import play.modules.reactivemongo.ReactiveMongoApi

@Singleton
class UserRepository @Inject()(val reactiveMongoApi: ReactiveMongoApi, val collectionName: String = "user") extends BaseRepository[User]
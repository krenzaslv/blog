package services

import com.google.inject.Inject
import models.User
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.collection.JSONCollection
import models.JsonFormats._

class UserService @Inject()(reactiveMongoApi: ReactiveMongoApi) extends MongoCRUDService[User]{
  override def collection: JSONCollection = reactiveMongoApi.db.collection("users")
}

package models.user

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.LoginInfo
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.ReadPreference
import reactivemongo.bson.BSONDocument
import scala.concurrent.ExecutionContext.Implicits.global
import java.util.UUID
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection._
import scala.concurrent.Future


class UserDaoMongo @Inject()(reactiveMongoApi: ReactiveMongoApi) extends UserDao {

  override def collection: JSONCollection = reactiveMongoApi.db.collection(TABLE_NAME)

  override def find(userID: UUID): Future[Option[User]] = {
    collection.find(Json.obj("userID" -> userID)).one[User]
  }

  override def find(loginInfo: LoginInfo): Future[Option[User]] = {
    collection.find(Json.obj("loginInfo" -> loginInfo)).one[User]
  }

  override def findAll: Future[List[User]] = {
    collection.
      find(Json.obj()).
      cursor[User](readPreference = ReadPreference.primary).
      collect[List](DEFAULT_MAX)
  }

  override def save(user: User): Future[User] = {
    collection.insert(user)
    Future.successful(user)
  }

  override def delete(userID: UUID): Future[WriteResult] = {
    collection.remove(Json.obj("userID" -> userID))
  }

  override def update(user: User): Future[User] = {
    collection.update(BSONDocument("userID" -> user.userID.toString), user)
    Future.successful(user)
  }
}
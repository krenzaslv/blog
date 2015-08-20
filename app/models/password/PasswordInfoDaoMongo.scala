package models.password

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.impl.daos.DelegableAuthInfoDAO
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.collection.JSONCollection
import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable
import scala.concurrent.Future
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection._

class PasswordInfoDAOMongo @Inject()(reactiveMongoApi: ReactiveMongoApi) extends DelegableAuthInfoDAO[PasswordInfo] {

  val TABLE_NAME: String = "PasswordInfo"

  implicit val passwordInfoFormat = Json.format[PasswordInfo]
  implicit val persistentPasswordInfoFormat = Json.format[PersistentPasswordInfo]

  def collection: JSONCollection = reactiveMongoApi.db.collection(TABLE_NAME)

  override def update(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    Future.successful(authInfo)
  }

  override def remove(loginInfo: LoginInfo): Future[Unit] = {
    Future.successful(())
  }

  override def save(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    find(loginInfo).flatMap {
      case Some(_) => update(loginInfo, authInfo)
      case None => add(loginInfo, authInfo)
    }
  }

  override def find(loginInfo: LoginInfo): Future[Option[PasswordInfo]] = {
    val passwordInfo: Future[Option[PasswordInfo]] = collection
      .find(Json.obj("loginInfo" -> loginInfo))
      .one[PasswordInfo]

    passwordInfo.flatMap {
      case None =>
        Future.successful(Option.empty[PasswordInfo])
      case Some(passwordInfo) =>
        Future(Some(passwordInfo))
    }
  }

  override def add(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] ={
    collection.insert(PersistentPasswordInfo(loginInfo, authInfo))
    Future.successful(authInfo)
  }
}

object PasswordInfoDAOMongo {

  var data: mutable.HashMap[LoginInfo, PasswordInfo] = mutable.HashMap()
}

case class PersistentPasswordInfo(loginInfo: LoginInfo, authInfo: PasswordInfo)
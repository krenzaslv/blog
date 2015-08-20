package models.password

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.impl.daos.DelegableAuthInfoDAO
import scala.concurrent.Future
import play.modules.reactivemongo._
import play.modules.reactivemongo.json.collection.JSONCollection
import scala.concurrent.ExecutionContext.Implicits.global
import PersistentPasswordInfo._
import play.modules.reactivemongo.json.collection._
import play.api.libs.json._
import json._


class PasswordInfoDAOMongo @Inject()(reactiveMongoApi: ReactiveMongoApi) extends DelegableAuthInfoDAO[PasswordInfo] {


  def collection: JSONCollection = reactiveMongoApi.db.collection(TABLE_NAME)

  val TABLE_NAME: String = "PasswordInfo"

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
    val passwordInfo: Future[Option[PersistentPasswordInfo]] = collection
      .find(Json.obj("loginInfo" -> loginInfo))
      .one[PersistentPasswordInfo]

    passwordInfo.flatMap {
      case None =>
        Future.successful(Option.empty[PasswordInfo])
      case Some(persistentPasswordInfo) =>
        Future(Some(persistentPasswordInfo.authInfo))
    }
  }

  override def add(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] ={
    collection.insert(PersistentPasswordInfo(loginInfo, authInfo))
    Future.successful(authInfo)
  }
}

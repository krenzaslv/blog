package controllers

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.Environment
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import db.farm.{FarmService, Farm}
import db.user.User
import play.api.i18n.MessagesApi


class FarmController @Inject()(messagesApi: MessagesApi, env: Environment[User, JWTAuthenticator], farmService: FarmService) extends BaseSecureController[Farm](messagesApi, env, farmService) {

}
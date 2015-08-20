package controllers

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.Environment
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import db.user.{UserService, User}
import play.api.i18n.MessagesApi
import play.api.libs.json.Json

import scala.concurrent.Future

class UserController @Inject()(messagesApi: MessagesApi, env: Environment[User, JWTAuthenticator], userService: UserService) extends BaseSecureController[User](messagesApi, env, userService) {

  def get = UserAwareAction.async { implicit request =>
    request.identity match {
      case Some(user) => Future.successful(Ok(Json.toJson(user)))
      case None => Future.successful(BadRequest)
    }
  }
}

package controllers

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.{Silhouette, Environment}
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import db.user.{UserService, User}
import play.api.i18n.MessagesApi
import play.api.libs.json.Json

import scala.concurrent.Future

class UserController @Inject()(val messagesApi: MessagesApi, val env: Environment[User, JWTAuthenticator], val userService: UserService) extends Silhouette[User, JWTAuthenticator] {

  def get = UserAwareAction.async { implicit request =>
    request.identity match {
      case Some(user) => Future.successful(Ok(Json.toJson(user)))
      case None => Future.successful(BadRequest)
    }
  }
}

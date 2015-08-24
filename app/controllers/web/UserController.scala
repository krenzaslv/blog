package controllers.web

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import db.user.{User, UserService}
import play.api.i18n.MessagesApi
import play.api.libs.json.Json

import scala.concurrent.Future

class UserController @Inject()(val messagesApi: MessagesApi, val env: Environment[User, JWTAuthenticator], val userService: UserService) extends Silhouette[User, JWTAuthenticator] {

  def get = SecuredAction.async { implicit request =>
    Future.successful(Ok(Json.toJson(request.identity)))
  }
}

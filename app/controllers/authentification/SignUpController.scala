package controllers.authentification

import com.google.inject.Inject
import com.mohiva.play.silhouette.api._
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.util.PasswordHasher
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import db.user.{UserService, User}
import formats.json.SignUpForm
import play.api.i18n.{Messages, MessagesApi}
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.Json
import play.api.mvc.Action
import SignUpForm._
import scala.concurrent.Future

class SignUpController @Inject()(implicit val env: Environment[User, JWTAuthenticator], val messagesApi: MessagesApi, val userService: UserService, val authInfoRepository: AuthInfoRepository, val passwordHasher: PasswordHasher) extends Silhouette[User, JWTAuthenticator] {

  def signUp = Action.async(parse.json) { implicit request =>
    /*   request.body.validate[SignUpForm].map { data =>
      val loginInfo = LoginInfo(CredentialsProvider.ID, data.email)
      userService.retrieve(loginInfo).flatMap {

        case Some(user) =>
          Future.successful(BadRequest(Json.obj("message" -> Messages("user.exists"))))
        case None =>
          val authInfo = passwordHasher.hash(data.password)
          val user = User(
            loginInfo = loginInfo,
            firstName = Some(data.firstName),
            lastName = Some(data.lastName),
            email = Some(data.email)
          )
          for {
            user <- userService.save(user)
            authInfo <- authInfoRepository.add(loginInfo, authInfo)
            authenticator <- env.authenticatorService.create(loginInfo)
            token <- env.authenticatorService.init(authenticator)
          } yield {
            env.eventBus.publish(SignUpEvent(user, request, request2Messages))
            env.eventBus.publish(LoginEvent(user, request, request2Messages))
            Ok(Json.obj("token" -> token))
          }
      }
    }.recoverTotal {
      case error =>
        Future.successful(Unauthorized(Json.obj("message" -> Messages("invalid.data"))))
    }
  } */
    Future.successful(Unauthorized(Json.obj("message" -> Messages("invalid.data"))))
  }
}

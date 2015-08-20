package controllers.authentification

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.{Environment, LogoutEvent, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import models.user.User
import play.api.i18n.MessagesApi

class SignOut @Inject()(
                         val messagesApi: MessagesApi,
                         val env: Environment[User, JWTAuthenticator]
                         )
  extends Silhouette[User, JWTAuthenticator] {

  def signOut = SecuredAction.async { implicit request =>
    env.eventBus.publish(LogoutEvent(request.identity, request, request2Messages))
    env.authenticatorService.discard(request.authenticator, Ok)
  }

}

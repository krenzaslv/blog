package controllers


import com.mohiva.play.silhouette.api.{Silhouette, Environment}
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import db.user.User
import play.api.i18n.MessagesApi
import java.util.UUID
import db.BaseService
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.{Format, Json}
import play.api.mvc.Action

import scala.concurrent.Future

class BaseSecureController[E: Format](val messagesApi: MessagesApi, val env: Environment[User, JWTAuthenticator], val service: BaseService[E]) extends Silhouette[User, JWTAuthenticator] {

  def create = UserAwareAction.async(parse.json) { implicit request =>
    request.identity match {
      case Some(user) =>
        request.body.validate[E].map { entity =>
          service.save(entity, user.userID).map { lastError =>
            Logger.debug(s"Successfully inserted with LastError: $lastError")
            Created
          }
        }.getOrElse(Future.successful(BadRequest("invalid json")))
      case None => Future.successful(Unauthorized)
    }
  }

  def list = Action.async {
    service.findAll.flatMap { entityList =>
      Future.successful(Ok(Json.toJson(entityList)))
    }
  }

  def get(id: String) = Action.async {
    service.find(UUID.fromString(id)).map(
      _.fold(
        NotFound(s"Entity #$id not found")
      )(entity =>
        Ok(Json.toJson(entity)))
    )
  }

  def delete(id: String) = UserAwareAction.async(parse.json) { implicit request =>
    request.identity match {
      case Some(user) =>
        service.delete(UUID.fromString(id), user.userID).map {
          case result if result.ok => Ok
          case result => BadRequest
        }
      case None => Future.successful(Unauthorized)
    }
  }

  def update = UserAwareAction.async(parse.json) { implicit request =>
    request.identity match {
      case Some(user) =>
        request.body.validate[E].map { entity =>
          service.save(entity, user.userID).map { lastError =>
            Logger.debug(s"Successfully inserted with LastError: $lastError")
            Ok(Json.toJson(entity))
          }
        }.getOrElse(Future.successful(BadRequest("invalid json")))
      case None => Future.successful(Unauthorized)
    }
  }
}

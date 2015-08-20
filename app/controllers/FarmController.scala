package controllers

import java.util.UUID

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.{Silhouette, Environment}
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import db.farm.{FarmService, Farm}
import db.user.User
import forms.FarmForm
import play.api.Logger
import play.api.i18n.MessagesApi
import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future


class FarmController @Inject()(val messagesApi: MessagesApi, val env: Environment[User, JWTAuthenticator], val farmService: FarmService) extends Silhouette[User, JWTAuthenticator] {

  def create = UserAwareAction.async(parse.json) { implicit request =>
    request.identity match {
      case Some(user) =>
        request.body.validate[FarmForm].map { farmForm =>
          //TODO make this cleaner
          val newFarm = Farm(new UUID(0L, 0L), farmForm.name, farmForm.description, farmForm.website, farmForm.email, user.userID, farmForm.offers)
          farmService.save(newFarm).map { lastError =>
            Logger.debug(s"Successfully inserted with LastError: $lastError")
            Created
          }
        }.getOrElse(Future.successful(BadRequest("invalid json")))
      case None => Future.successful(Unauthorized)
    }
  }

  def list = Action.async {
    farmService.findAll.flatMap { entityList =>
      Future.successful(Ok(Json.toJson(entityList)))
    }
  }

  def findAllByUserId = UserAwareAction.async(parse.json) { implicit request =>
    request.identity match {
      case Some(user) =>
        farmService.find(user.userID).flatMap { entityList =>
          Future.successful(Ok(Json.toJson(entityList)))
        }
    }
  }


  def get(id: String) = Action.async {
    farmService.find(UUID.fromString(id)).map(
      _.fold(
        NotFound(s"Entity #$id not found")
      )(entity =>
        Ok(Json.toJson(entity)))
    )
  }

  def delete(id: String) = UserAwareAction.async(parse.json) { implicit request =>
    request.identity match {
      case Some(user) =>
        farmService.delete(UUID.fromString(id), user.userID).map {
          case result if result.ok => Ok
          case result => BadRequest
        }
      case None => Future.successful(Unauthorized)
    }
  }

  def update(id: String) = UserAwareAction.async(parse.json) { implicit request =>
    request.identity match {
      case Some(user) =>
        request.body.validate[FarmForm].map { farmForm =>
          val newFarm = Farm(UUID.fromString(id), farmForm.name, farmForm.description, farmForm.website, farmForm.email, user.userID, farmForm.offers)
          farmService.save(newFarm).map { lastError =>
            Logger.debug(s"Successfully inserted with LastError: $lastError")
            Ok(Json.toJson(newFarm))
          }
        }.getOrElse(Future.successful(BadRequest("invalid json")))
      case None => Future.successful(Unauthorized)
    }
  }
}
package controllers

import java.util.UUID

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.{Silhouette, Environment}
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import db.farm.{FarmService, Farm}
import db.user.User
import formats.json.FarmForm
import play.api.i18n.MessagesApi
import play.api.mvc.Action
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future
import play.api.libs.json.Json
import FarmForm._

class FarmController @Inject()(val messagesApi: MessagesApi, val env: Environment[User, JWTAuthenticator], val farmService: FarmService) extends Silhouette[User, JWTAuthenticator] {

  def list = Action.async {
    farmService.findAll.flatMap { entityList =>
      Future.successful(Ok(Json.toJson(entityList)))
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

  def create = UserAwareAction.async(parse.json) { implicit request =>
    request.identity match {
      case Some(user) =>
        request.body.validate[FarmForm].map { farmForm =>
          //TODO make this cleaner evt transformers for formats
          val newFarm = Farm(UUID.randomUUID(), farmForm.name, farmForm.description, farmForm.website, farmForm.email, user.userID, None)
          farmService.save(newFarm).map { farm =>
            Created(Json.toJson(farm))
          }
        }.getOrElse(Future.successful(BadRequest("invalid json")))
      case None => Future.successful(Unauthorized)
    }
  }

  def findByUserId = UserAwareAction.async(parse.json) { implicit request =>
    request.identity match {
      case Some(user) =>
        farmService.find(user.userID).flatMap { entityList =>
          Future.successful(Ok(Json.toJson(entityList)))
        }
    }
  }

  def delete(id: String) = UserAwareAction.async(parse.json) { implicit request =>
    request.identity match {
      case Some(user) =>
        farmService.deleteById(user.userID, UUID.fromString(id)).map {
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
          val farm = Farm(UUID.fromString(id), farmForm.name, farmForm.description, farmForm.website, farmForm.email, user.userID, farmForm.offers)
          farmService.save(farm).map { createdfarm =>
            Ok(Json.toJson(createdfarm))
          }
        }.getOrElse(Future.successful(BadRequest("invalid json")))
      case None => Future.successful(Unauthorized)
    }
  }
}
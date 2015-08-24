package controllers.web

import java.util.UUID

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import db.farm.{Farm, FarmService}
import db.user.User
import formats.json.FarmForm
import formats.json.FarmForm._
import play.api.i18n.MessagesApi
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.Json
import play.api.mvc.Action

import scala.concurrent.Future

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

  def create = SecuredAction.async(parse.json) { implicit request =>
    request.body.validate[FarmForm].map { farmForm =>
      //TODO make this cleaner evt transformers for formats
      val newFarm = Farm(UUID.randomUUID(), farmForm.name, farmForm.description, farmForm.website, farmForm.email, request.identity.userID, None)
      farmService.save(newFarm).map { farm =>
        Created(Json.toJson(farm))
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def findByUserId = SecuredAction.async(parse.json) { implicit request =>
    farmService.find(request.identity.userID).flatMap { entityList =>
      Future.successful(Ok(Json.toJson(entityList)))
    }
  }

  def delete(id: String) = SecuredAction.async { implicit request =>
    farmService.deleteById(request.identity.userID, UUID.fromString(id)).map {
      case result if result.ok => Ok
      case result => BadRequest
    }
  }

  def update(id: String) = SecuredAction.async(parse.json) { implicit request =>
    request.body.validate[FarmForm].map { farmForm =>
      val farm = Farm(UUID.fromString(id), farmForm.name, farmForm.description, farmForm.website, farmForm.email, request.identity.userID, farmForm.offers)
      farmService.save(farm).map { createdFarm =>
        Ok(Json.toJson(createdFarm))
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }
}
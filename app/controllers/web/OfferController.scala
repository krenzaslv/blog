package controllers.web

import java.util.UUID

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import db.offer.Offer._
import db.offer.{Offer, OfferService}
import db.user.User
import formats.json.OfferForm
import formats.json.OfferForm._
import play.api.i18n.MessagesApi
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.Json
import play.api.mvc.Action

import scala.concurrent.Future

class OfferController @Inject()(val messagesApi: MessagesApi, val env: Environment[User, JWTAuthenticator], val offerService: OfferService) extends Silhouette[User, JWTAuthenticator] {

  def list = Action.async {
    offerService.findAll.flatMap { entityList =>
      Future.successful(Ok(Json.toJson(entityList)))
    }
  }

  def get(id: String) = Action.async {
    offerService.find(UUID.fromString(id)).map(
      _.fold(
        NotFound(s"Entity #$id not found")
      )(entity =>
        Ok(Json.toJson(entity)))
    )
  }

  def create(farmID: String) = SecuredAction.async(parse.json) { implicit request =>
    request.body.validate[OfferForm].map { offerForm =>
      //TODO make this cleaner evt transformers for formats
      val offer = Offer(UUID.randomUUID(), offerForm.name, offerForm.description, offerForm.price)
      offerService.save(request.identity.userID, UUID.fromString(farmID), offer).map { farm =>
        Created(Json.toJson(farm))
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def delete(id: String) = SecuredAction.async { implicit request =>
    offerService.delete(request.identity.userID, UUID.fromString(id)).map {
      case result if result.ok => Ok
      case result => BadRequest
    }
  }
}
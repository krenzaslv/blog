package controllers

import java.util.UUID

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.{Silhouette, Environment}
import com.mohiva.play.silhouette.impl.authenticators.JWTAuthenticator
import db.offer.{Offer, OfferService}
import db.user.User
import formats.json.OfferForm
import play.api.i18n.MessagesApi
import play.api.libs.json.Json
import play.api.mvc.Action
import play.api.libs.concurrent.Execution.Implicits._
import OfferForm._
import Offer._
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

  def create(farmID: String) = UserAwareAction.async(parse.json) { implicit request =>
    request.identity match {
      case Some(user) =>
        request.body.validate[OfferForm].map { offerForm =>
          //TODO make this cleaner evt transformers for formats
          val offer = Offer(UUID.randomUUID(), offerForm.name, offerForm.description, offerForm.price)
          offerService.save(user.userID, UUID.fromString(farmID), offer).map { farm =>
            Created(Json.toJson(farm))
          }
        }.getOrElse(Future.successful(BadRequest("invalid json")))
      case None => Future.successful(Unauthorized)
    }
  }

  def delete(id: String) = UserAwareAction.async { implicit request =>
    request.identity match {
      case Some(user) =>
        offerService.delete(user.userID, UUID.fromString(id)).map {
          case result if result.ok => Ok
          case result => BadRequest
        }
      case None => Future.successful(Unauthorized)
    }
  }
}
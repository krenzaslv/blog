package controllers

import com.google.inject.Inject
import play.api.Logger
import play.api.libs.json.{JsError, JsSuccess, Json, Format}
import play.api.mvc.{Action, Controller}
import services.{CRUDService, MongoCRUDService}
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

abstract class CRUDController[E: Format](service: CRUDService[E]) extends Controller {

  def create = Action.async(parse.json) { implicit request =>
    request.body.validate[E].map { entity =>
      service.create(entity).map { lastError =>
        Logger.debug(s"Successfully inserted with LastError: $lastError")
        Created
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def list = Action.async {
    service.findAll.map { entityList =>
      Ok(Json.toJson(entityList))
    }
  }

  def get(id: String) = Action.async {
    service.findById(id).map(
      _.fold(
        NotFound(s"Entity #$id not found")
      )(entity =>
        Ok(Json.toJson(entity)))
    )
  }

  def delete(id: String) = Action.async {
    service.delete(id).map {
      case JsSuccess(_, _) => Ok
      case JsError(_) => BadRequest
    }
  }

  def update(id: String) = Action.async(parse.json) { implicit request =>
    request.body.validate[E].map { entity =>
      service.update(id, entity).map { lastError =>
        Logger.debug(s"Successfully inserted with LastError: $lastError")
        Ok(Json.toJson(entity))
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }
}

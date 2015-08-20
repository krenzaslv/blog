package controllers.admin

import java.util.UUID

import db.BaseService
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.{Format, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

abstract class BaseAdminController[E: Format](val service: BaseService[E]) extends Controller {

  def create = Action.async(parse.json) { implicit request =>
    request.body.validate[E].map { entity =>
      service.save(entity).map { lastError =>
        Logger.debug(s"Successfully inserted with LastError: $lastError")
        Created
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
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

  def delete(id: String) = Action.async {
    service.delete(UUID.fromString(id)).map {
      case result if result.ok => Ok
      case result => BadRequest
    }
  }

  def update = Action.async(parse.json) { implicit request =>
    request.body.validate[E].map { entity =>
      service.save(entity).map { lastError =>
        Logger.debug(s"Successfully inserted with LastError: $lastError")
        Ok(Json.toJson(entity))
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }
}

package controllers

import db.BaseService
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.{Format, Json}
import play.api.mvc.{Action, Controller}
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Future

abstract class BaseController[E: Format](val service: BaseService[E]) extends Controller {

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
    BSONObjectID.parse(id).map { id =>
      service.find(id).map(
        _.fold(
          NotFound(s"Entity #$id not found")
        )(entity =>
          Ok(Json.toJson(entity)))
      )
    }.getOrElse(Future.successful(NotFound))
  }

  def delete(id: String) = Action.async {
    BSONObjectID.parse(id).map { id =>
      service.delete(id).map {
        lastError =>
          Logger.debug(s"Successfully deleted with LastError: $lastError")
          Ok
      }
    }.getOrElse(Future.successful(BadRequest))
  }


  def update = Action.async(parse.json) {
    implicit request =>
      request.body.validate[E].map {
        entity =>
          service.save(entity).map {
            lastError =>
              Logger.debug(s"Successfully inserted with LastError: $lastError")
              Ok(Json.toJson(entity))
          }
      }.getOrElse(Future.successful(BadRequest("invalid json")))
  }
}

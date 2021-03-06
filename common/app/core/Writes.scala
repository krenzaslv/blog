package core

import play.api.Logger
import play.api.libs.json.{Format, Json}
import play.api.mvc.Action
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future

trait Writes[T <: BaseModel] extends BaseController[T] {

  def create(implicit format: Format[T]) = Action.async(parse.json) { implicit request =>
    request.body.validate[T].map { entity =>
      service.save(entity).map { lastError =>
        Logger.debug(s"Successfully inserted with LastError: $lastError")
        Created
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  /*def delete(id: String)(implicit format: Format[T]) = Action.async {
    BSONObjectID.parse(id).map { id =>
      service.delete(id).map {
        lastError =>
          Logger.debug(s"Successfully deleted with LastError: $lastError")
          Ok
      }
    }.getOrElse(Future.successful(BadRequest))
  }
  */

  def update(implicit format: Format[T]) = Action.async(parse.json) {
    implicit request =>
      request.body.validate[T].map {
        entity =>
          service.save(entity).map {
            lastError =>
              Logger.debug(s"Successfully inserted with LastError: $lastError")
              Ok(Json.toJson(entity))
          }
      }.getOrElse(Future.successful(BadRequest("invalid json")))
  }
}

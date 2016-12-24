package core

import play.api.libs.json.{Format, Json}
import play.api.mvc.Action
import reactivemongo.bson.BSONObjectID

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future

trait Reads[T <: BaseModel] extends BaseController[T] {

  def list(implicit format: Format[T]) = Action.async {
    service.findAll.flatMap { entityList =>
      Future.successful(Ok(Json.toJson(entityList)))
    }
  }

  def get(id: String)(implicit format: Format[T]) = Action.async {
    BSONObjectID.parse(id).map { id =>
      service.find(id).map(
        _.fold(
          NotFound(s"Entity #$id not found")
        )(entity =>
          Ok(Json.toJson(entity)))
      )
    }.getOrElse(Future.successful(BadRequest))
  }
}

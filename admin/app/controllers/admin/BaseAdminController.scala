package controllers.admin

import controllers.{BaseController, Reads, Writes}
import db.{BaseModel, BaseRepository, BaseService}

class BaseAdminController[E <: BaseModel] extends BaseController with BaseService[E] with BaseRepository[E] with Reads[E] with Writes[E]

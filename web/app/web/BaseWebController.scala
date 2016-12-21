package web

import controllers.{BaseController, Reads}
import db.{BaseModel, BaseRepository, BaseService}

class BaseWebController[E <: BaseModel] extends BaseController with BaseService[E] with BaseRepository[E] with Reads[E]
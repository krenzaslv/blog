package controllers.admin

import core.{BaseController, BaseModel}

trait BaseAdminController[T <: BaseModel] extends BaseController[T]

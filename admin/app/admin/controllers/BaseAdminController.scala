package admin.controllers

import core.{BaseController, BaseModel}

/**
  * Created by daniel on 24.12.16.
  */
trait BaseAdminController[T <: BaseModel] extends BaseController[T]

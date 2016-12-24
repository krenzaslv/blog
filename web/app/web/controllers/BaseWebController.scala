package web.controllers

import core.{BaseController, BaseModel}

/**
  * Created by daniel on 24.12.16.
  */
trait BaseWebController[T <: BaseModel] extends BaseController[T]

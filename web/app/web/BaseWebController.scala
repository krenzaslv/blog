package web

import core.{BaseController, BaseModel}


trait BaseWebController[T <: BaseModel] extends BaseController[T]
package core

import play.api.mvc.Controller

trait BaseController[T <: BaseModel] extends Controller {

  val service : BaseService[T]
}

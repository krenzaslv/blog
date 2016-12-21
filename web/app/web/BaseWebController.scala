package web

import controllers.{BaseController, Reads}
import db.BaseService
import play.api.libs.json.Format

class BaseWebController[E : Format](service : BaseService[E]) extends BaseController[E](service) with Reads[E]
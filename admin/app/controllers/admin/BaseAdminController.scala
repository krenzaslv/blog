package controllers.admin

import controllers.{BaseController, Reads, Writes}
import db.BaseService
import play.api.libs.json.Format

class BaseAdminController[E: Format](service: BaseService[E]) extends BaseController[E](service) with Reads[E] with Writes[E]

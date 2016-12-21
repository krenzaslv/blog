package controllers

import db.BaseService}
import play.api.libs.json.Format
import play.api.mvc.Controller

abstract class BaseController[E:Format](val service: BaseService[E]) extends Controller

package core

import play.api.mvc._

class Application extends Controller {

  def index = Action {
    Ok("Your new application is ready.")
  }

}

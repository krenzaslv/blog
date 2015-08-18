package controllers

import com.google.inject.Inject
import models.User
import services.UserService
import models.JsonFormats._

class UserController @Inject()(userService: UserService) extends CRUDController[User](userService) {

}

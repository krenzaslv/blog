package controllers.admin

import com.google.inject.Inject
import controllers.BaseController
import db.user.{User, UserService}

class UserAdminController @Inject()(userService: UserService) extends BaseController[User](userService) {

  //Signup handles create
  override def create = ???

}

package controllers.admin

import com.google.inject.Inject
import db.user.{User, UserService}

class UserAdminController @Inject()(userService: UserService) extends BaseAdminController[User](userService) {

  //Signup handles create
  override def create = ???

}

package web

import com.google.inject.Inject
import db.user.UserService

class UserController @Inject()(val userService: UserService) {}

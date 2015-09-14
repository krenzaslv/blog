package controllers.web

import com.google.inject.Inject
import controllers.BaseController
import db.post.{PostService, Post}

class PostController @Inject()(postService: PostService) extends BaseController[Post](postService){

}

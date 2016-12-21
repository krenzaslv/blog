package web

import com.google.inject.Inject
import controllers.BaseController
import db.post.{Post, PostService}

class PostController @Inject()(postService: PostService) extends BaseController[Post](postService){

}

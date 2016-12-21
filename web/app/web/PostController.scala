package web

import com.google.inject.Inject
import db.post.{Post, PostService}

class PostController @Inject()(postService: PostService) extends BaseWebController[Post](postService)
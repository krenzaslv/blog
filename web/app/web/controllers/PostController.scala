package web.controllers

import com.google.inject.{Inject, Singleton}
import core.{Reads, Writes}
import post.{Post, PostService}

@Singleton
class PostController @Inject()(val service: PostService) extends BaseWebController[Post] with Reads[Post] with Writes[Post]
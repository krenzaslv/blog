'use strict'

class CreatePostCtrl

  constructor: (@$log, @$location, @$routeParams, @PostService, @$scope) ->
    @$log.debug "constructing CreateUserController"
    @post= {}


  createPost: () ->
    @$log.debug "createPost() #{angular.toJson(@post, true)}"
    @PostService.createPost(@post)
    .then(
      (data) =>
        @$log.debug "Promise returned #{data} Post"
        @post = data
        @$location.path("/")
    ,
      (error) =>
        @$log.error "Unable to create Post: #{error}"
    )


angular.module 'uiApp'
  .controller 'CreatepostCtrl', CreatePostCtrl

'use strict'

###*
 # @ngdoc function
 # @name uiApp.controller:PostCtrl
 # @description
 # # PostCtrl
 # Controller of the uiApp
###

class PostCtrl

  constructor: (@$log, @PostService) ->
    @$log.debug "constructing PostController"
    @posts = []
    @getAllPosts()

  getAllPosts: () ->
    @$log.debug "getAllPosts()"

    @PostService.listPosts()
    .then(
      (data) =>
        @$log.debug "Promise returned #{data.length} Posts"
        @posts = data
    ,
      (error) =>
        @$log.error "Unable to get Posts: #{error}"
    )

angular.module 'uiApp'
.controller 'PostCtrl', PostCtrl

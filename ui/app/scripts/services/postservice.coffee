'use strict'

###*
 # @ngdoc service
 # @name uiApp.PostService
 # @description
 # # PostService
 # Service in the uiApp.
###

class PostService

  @headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}
  @defaultConfig = {headers: @headers}

  constructor: (@$log, @$http, @$q) ->
    @$log.debug "constructing PostService"

  listPosts: () ->
    @$log.debug "listPosts()"
    deferred = @$q.defer()

    @$http.get("/post")
    .success((data, status, headers) =>
      @$log.info("Successfully listed Posts - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to list Posts - status #{status}")
      deferred.reject(data);
    )
    deferred.promise

  createPost: (post) ->
    @$log.debug "createPost #{angular.toJson(post, true)}"
    deferred = @$q.defer()

    @$http.post('/post', post)
    .success((data, status, headers) =>
      @$log.info("Successfully created Post - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to create Post - status #{status}")
      deferred.reject(data);
    )
    deferred.promise

angular.module 'uiApp'
.service 'PostService', PostService

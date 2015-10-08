'use strict'

class PictureService

  @headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}
  @defaultConfig = {headers: @headers}

  constructor: (@$log, @$http, @$q) ->
    @$log.debug "constructing PictureService"

  listPictures: () ->
    @$log.debug "listPictures()"
    deferred = @$q.defer()

    @$http.get("/picture")
    .success((data, status, headers) =>
      @$log.info("Successfully listed Pictures - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to list Pictures - status #{status}")
      deferred.reject(data);
    )
    deferred.promise

  getUser: (id) ->
    @$log.debug "getpPicture()"
    deferred = @$q.defer()

    @$http.get("/picture/#{id}")
    .success((data, status, headers) =>
      @$log.info("Successfully retrieve Picture - status #{status}")
      deferred.resolve(data)
    )
    .error((data, status, headers) =>
      @$log.error("Failed to retrieve Picture - status #{status}")
      deferred.reject(data);
    )

angular.module 'uiApp'
.service 'PictureService', PictureService

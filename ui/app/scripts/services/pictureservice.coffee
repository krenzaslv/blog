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

angular.module 'uiApp'
.service 'PictureService', PictureService

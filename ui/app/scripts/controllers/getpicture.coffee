'use strict'

class GetPictureCtrl

  constructor: (@$log, @PictureService) ->
    @$log.debug "constructing PictureController"
    @pictures = []

  getAllPictures: () ->
    @$log.debug "getAllPictures()"

    @PostService.listPictures()
    .then(
      (data) =>
        @$log.debug "Promise returned #{data.length} Pictures"
        @pictures = data
    ,
      (error) =>
        @$log.error "Unable to get Pictures: #{error}"
    )

  getPicture: (id) ->
    @$log.debug "getPicture()"

    @PostService.getPicture(id)
    .then(
      (data) =>
        @$log.debug "Promise returned #{data.length} Pictures"
        @pictures = data
    ,
      (error) =>
        @$log.error "Unable to get Pictures: #{error}"
    )

angular.module 'uiApp'
.controller 'GetpictureCtrl', GetPictureCtrl


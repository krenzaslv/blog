'use strict'

class GetPictureCtrl

  constructor: (@$log, @PictureService) ->
    @$log.debug "constructing PictureController"
    @pictureUrls = []
    @getAllPictureUrls()

  getAllPictureUrls: () ->
    @$log.debug "getAllPictures()"

    @PictureService.listPictures()
    .then(
      (data) =>
        @$log.debug "Promise returned #{data.length} Pictures"
        @pictureUrls = data
    ,
      (error) =>
        @$log.error "Unable to get Pictures: #{error}"
    )

  getPicture: (id) ->
    @$log.debug "getPicture()"

    @PictureService.getPicture(id)
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


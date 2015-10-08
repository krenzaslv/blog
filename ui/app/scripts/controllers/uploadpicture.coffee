'use strict'

class UploadPictureCtrl

  uploadComplete: (content) ->
    $scope.response = JSON.parse(content);

angular.module 'uiApp'
.controller 'UploadpictureCtrl', UploadPictureCtrl


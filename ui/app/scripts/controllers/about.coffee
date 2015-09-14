'use strict'

###*
 # @ngdoc function
 # @name uiApp.controller:AboutCtrl
 # @description
 # # AboutCtrl
 # Controller of the uiApp
###
angular.module 'uiApp'
  .controller 'AboutCtrl', ->
    @awesomeThings = [
      'HTML5 Boilerplate'
      'AngularJS'
      'Karma'
    ]
    return

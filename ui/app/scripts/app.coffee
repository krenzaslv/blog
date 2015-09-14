'use strict'

###*
 # @ngdoc overview
 # @name uiApp
 # @description
 # # uiApp
 #
 # Main module of the application.
###
angular
.module 'uiApp', [
  'ngAnimate',
  'ngAria',
  'ngCookies',
  'ngMessages',
  'ngResource',
  'ngRoute',
  'ngSanitize',
  'ngTouch',
  'textAngular'
]
.config ($routeProvider) ->
  $routeProvider
  .when '/',
    templateUrl: 'views/main.html'
    controller: 'MainCtrl'
    controllerAs: 'main'
  .when '/about',
    templateUrl: 'views/about.html'
    controller: 'AboutCtrl'
    controllerAs: 'about'
  .when '/post',
    templateUrl: 'views/post.html'
    controller: 'PostCtrl'
    controllerAs: 'post'
  .otherwise
      redirectTo: '/'

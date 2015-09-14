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
  .when '/index',
    templateUrl: 'views/main.html'
    controller: 'MainCtrl'
    controllerAs: 'main'
  .when '/about',
    templateUrl: 'views/about.html'
    controller: 'AboutCtrl'
    controllerAs: 'about'
  .when '/',
    templateUrl: 'views/post.html'
    controller: 'PostCtrl'
    controllerAs: 'pc'
  .when '/post/create',
    templateUrl: 'views/post_create.html'
    controller: 'CreatepostCtrl'
    controllerAs: 'cpc'
  .otherwise
      redirectTo: '/'


'use strict'

describe 'Controller: PostCtrl', ->

  # load the controller's module
  beforeEach module 'uiApp'

  PostCtrl = {}

  scope = {}

  # Initialize the controller and a mock scope
  beforeEach inject ($controller, $rootScope) ->
    scope = $rootScope.$new()
    PostCtrl = $controller 'PostCtrl', {
      # place here mocked dependencies
    }

  it 'should attach a list of awesomeThings to the scope', ->
    expect(PostCtrl.awesomeThings.length).toBe 3

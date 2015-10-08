'use strict'

describe 'Controller: PictureCtrl', ->

  # load the controller's module
  beforeEach module 'uiApp'

  PictureCtrl = {}

  scope = {}

  # Initialize the controller and a mock scope
  beforeEach inject ($controller, $rootScope) ->
    scope = $rootScope.$new()
    PictureCtrl = $controller 'PictureCtrl', {
      # place here mocked dependencies
    }

  it 'should attach a list of awesomeThings to the scope', ->
    expect(PictureCtrl.awesomeThings.length).toBe 3

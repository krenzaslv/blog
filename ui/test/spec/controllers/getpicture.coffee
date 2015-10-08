'use strict'

describe 'Controller: GetpictureCtrl', ->

  # load the controller's module
  beforeEach module 'uiApp'

  GetpictureCtrl = {}

  scope = {}

  # Initialize the controller and a mock scope
  beforeEach inject ($controller, $rootScope) ->
    scope = $rootScope.$new()
    GetpictureCtrl = $controller 'GetpictureCtrl', {
      # place here mocked dependencies
    }

  it 'should attach a list of awesomeThings to the scope', ->
    expect(GetpictureCtrl.awesomeThings.length).toBe 3

'use strict'

describe 'Controller: UploadpictureCtrl', ->

  # load the controller's module
  beforeEach module 'uiApp'

  UploadpictureCtrl = {}

  scope = {}

  # Initialize the controller and a mock scope
  beforeEach inject ($controller, $rootScope) ->
    scope = $rootScope.$new()
    UploadpictureCtrl = $controller 'UploadpictureCtrl', {
      # place here mocked dependencies
    }

  it 'should attach a list of awesomeThings to the scope', ->
    expect(UploadpictureCtrl.awesomeThings.length).toBe 3

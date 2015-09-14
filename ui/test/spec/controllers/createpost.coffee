'use strict'

describe 'Controller: CreatepostCtrl', ->

  # load the controller's module
  beforeEach module 'uiApp'

  CreatepostCtrl = {}

  scope = {}

  # Initialize the controller and a mock scope
  beforeEach inject ($controller, $rootScope) ->
    scope = $rootScope.$new()
    CreatepostCtrl = $controller 'CreatepostCtrl', {
      # place here mocked dependencies
    }

  it 'should attach a list of awesomeThings to the scope', ->
    expect(CreatepostCtrl.awesomeThings.length).toBe 3

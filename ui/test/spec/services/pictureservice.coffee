'use strict'

describe 'Service: PictureService', ->

  # load the service's module
  beforeEach module 'uiApp'

  # instantiate service
  PictureService = {}
  beforeEach inject (_PictureService_) ->
    PictureService = _PictureService_

  it 'should do something', ->
    expect(!!PictureService).toBe true

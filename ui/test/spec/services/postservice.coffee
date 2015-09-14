'use strict'

describe 'Service: PostService', ->

  # load the service's module
  beforeEach module 'uiApp'

  # instantiate service
  PostService = {}
  beforeEach inject (_PostService_) ->
    PostService = _PostService_

  it 'should do something', ->
    expect(!!PostService).toBe true

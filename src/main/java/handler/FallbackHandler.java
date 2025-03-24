package handler;

import request.ParsedRequest;
import response.CustomHttpResponse;
import response.ResponseBuilder;
public class FallbackHandler implements BaseHandler {

  @Override
  public CustomHttpResponse handleRequest(ParsedRequest request) {
    // TODO
    return new ResponseBuilder()
            .setStatus("404 Not Found")
            .setBody("Endpoint not found")
            .build();
  }
}

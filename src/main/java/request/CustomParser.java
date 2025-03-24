package request;

public class CustomParser {

  // extract java useable values from a raw http request string
  // https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages
  public static ParsedRequest parse(String request){
    // TODO
    ParsedRequest parsedRequest = new ParsedRequest();

    String[] lines = request.split("\n");
    String[] requestLine = lines[0].split(" ");

    parsedRequest.setMethod(requestLine[0]);
    String pathWithQuery = requestLine[1];
    String[] pathAndQuery = pathWithQuery.split("\\?");
    parsedRequest.setPath(pathAndQuery[0]);

    if (pathAndQuery.length > 1) {
      String[] queryParams = pathAndQuery[1].split("&");

      for (String param : queryParams){
        String[] keyVal = param.split("=");
        parsedRequest.setQueryParam(keyVal[0], keyVal[1]);
      }
    }

    // Handling the body of the request, if present.
    if (lines.length > 1){
      parsedRequest.setBody(lines[lines.length - 1]);
    }

    return parsedRequest;
  }
}

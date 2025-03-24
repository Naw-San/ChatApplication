package response;

import dto.TransactionDto;
import handler.GsonTool;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CustomHttpResponseTest {

  @Test
  public void testHttpRes(){
    // Testing the app level api json
    String randKey = String.valueOf(Math.random());
    String rand = String.valueOf(Math.random());
    TransactionDto transactionDto =  new TransactionDto(String.valueOf(Math.random()));
    RestApiAppResponse response = new RestApiAppResponse(true,
        List.of(transactionDto), String.valueOf(Math.random()));
    CustomHttpResponse res = new ResponseBuilder()
        .setStatus("200 Ok").setVersion("HTTP/1.1")
        .setBody(GsonTool.gson.toJson(response))
        .setHeaders(Map.of(randKey, rand))
        .build();
    Assert.assertEquals(res.toString(), "HTTP/1.1 200 Ok\n"
        + randKey + ": " + rand +"\n\n" + GsonTool.gson.toJson(response));
  }

}

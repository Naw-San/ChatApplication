package handler;

import com.google.gson.reflect.TypeToken;
import dao.TransactionDao;
import dto.TransactionDto;
import org.testng.Assert;
import org.testng.annotations.Test;
import response.RestApiAppResponse;
import server.Server;

public class GetTransactionsHandlerTest {

    @Test(singleThreaded = true)
    public void createUserTest() {
        TransactionDao.reset();
        String userId = String.valueOf(Math.random());
        int messageCount = (int) (Math.random() * 10);
        TransactionDao transactionDao = TransactionDao.getInstance();

        for (int i = 0; i < messageCount; i++) {
            TransactionDto transactionDto1 = new TransactionDto(String.valueOf(Math.random()));
            transactionDto1.setUserId(userId);
            transactionDto1.setAmount(Math.random());
            transactionDao.put(transactionDto1);
        }

        String test1 = "GET /getTransactions?userId=" + userId + " HTTP/1.1\n"
                + "Host: test\n"
                + "Connection: Keep-Alive\n"
                + "\n";
        String response = Server.processRequest(test1);
        String[] responseParts = response.split("\n");
        Assert.assertEquals(responseParts[0], "HTTP/1.1 200 OK");
        RestApiAppResponse<TransactionDto> messages = GsonTool.gson.fromJson(responseParts[2],
                new TypeToken<RestApiAppResponse<TransactionDto>>() {
                }.getType());
        Assert.assertEquals(messages.data.size(), messageCount);
    }

    @Test(singleThreaded = true)
    public void createUserTest2() {
        TransactionDao.reset();
        String userId = String.valueOf(Math.random());

        String messageId1 = String.valueOf(Math.random());
        TransactionDto transactionDto1 = new TransactionDto(messageId1);
        transactionDto1.setUserId(userId);
        transactionDto1.setAmount(Math.random());

        TransactionDto transactionDto2 = new TransactionDto();
        transactionDto2.setUserId(userId);
        TransactionDao transactionDao = TransactionDao.getInstance();

        transactionDao.put(transactionDto1);
        transactionDao.put(transactionDto2);
        String test1 = "GET /getTransactions?userId=" + Math.random() + " HTTP/1.1\n"
                + "Host: test\n"
                + "Connection: Keep-Alive\n"
                + "\n";
        String response = Server.processRequest(test1);
        String[] responseParts = response.split("\n");
        Assert.assertEquals(responseParts[0], "HTTP/1.1 200 OK");
        RestApiAppResponse<TransactionDto> messages = GsonTool.gson.fromJson(responseParts[2],
                new TypeToken<RestApiAppResponse<TransactionDto>>() {
                }.getType());
        Assert.assertEquals(messages.data.size(), 0);
    }

}

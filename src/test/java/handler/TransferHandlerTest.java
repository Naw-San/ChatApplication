package handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.TransactionDao;
import dao.UserDao;
import dto.TransactionDto;
import dto.TransferRequestDto;
import dto.UserDto;
import org.testng.Assert;
import org.testng.annotations.Test;
import response.RestApiAppResponse;
import server.Server;
import testutils.TestUtils;

public class TransferHandlerTest {
    private static final Gson gson = new Gson();

    @Test(singleThreaded = true)
    public void transferTest1() {
        UserDao.reset();
        TransferRequestDto requestDto = new TransferRequestDto(0, TestUtils.makeRandomString(), TestUtils.makeRandomString());
        String test1 = "GET /transfer HTTP/1.1\n"
                + "Host: test\n"
                + "Connection: Keep-Alive\n"
                + "\n"
                + gson.toJson(requestDto);
        String response = Server.processRequest(test1);
        String[] responseParts = response.split("\n");
        Assert.assertEquals(responseParts[0], "HTTP/1.1 400 Bad Request");
        RestApiAppResponse<?> userRes = GsonTool.gson.fromJson(responseParts[2],
                new TypeToken<RestApiAppResponse<?>>() {
                }.getType());
        Assert.assertEquals(userRes.message, "Invalid from user.");
    }

    @Test(singleThreaded = true)
    public void transferTest2() {
        UserDao.reset();
        UserDto user1 = TestUtils.createUser();
        TransferRequestDto requestDto = new TransferRequestDto(0, user1.getUniqueId(), null);
        String test1 = "GET /transfer HTTP/1.1\n"
                + "Host: test\n"
                + "Connection: Keep-Alive\n"
                + "\n"
                + gson.toJson(requestDto);
        String response = Server.processRequest(test1);
        String[] responseParts = response.split("\n");
        Assert.assertEquals(responseParts[0], "HTTP/1.1 400 Bad Request");
        RestApiAppResponse<?> userRes = GsonTool.gson.fromJson(responseParts[2],
                new TypeToken<RestApiAppResponse<?>>() {
                }.getType());
        Assert.assertEquals(userRes.message, "Invalid user to transfer.");
    }

    @Test(singleThreaded = true)
    public void transferTest3() {
        UserDao.reset();
        UserDto user1 = TestUtils.createUser();
        UserDto user2 = TestUtils.createUser();

        TransferRequestDto requestDto = new TransferRequestDto(0, user1.getUniqueId(), TestUtils.makeRandomString());
        String test1 = "GET /transfer HTTP/1.1\n"
                + "Host: test\n"
                + "Connection: Keep-Alive\n"
                + "\n"
                + gson.toJson(requestDto);
        String response = Server.processRequest(test1);
        String[] responseParts = response.split("\n");
        Assert.assertEquals(responseParts[0], "HTTP/1.1 400 Bad Request");
        RestApiAppResponse<?> userRes = GsonTool.gson.fromJson(responseParts[2],
                new TypeToken<RestApiAppResponse<?>>() {
                }.getType());
        Assert.assertEquals(userRes.message, "Invalid user to transfer.");
    }

    @Test(singleThreaded = true)
    public void transferTest4() {
        UserDao.reset();
        UserDto user1 = TestUtils.createUser();
        UserDto user2 = TestUtils.createUser();

        TransferRequestDto requestDto = new TransferRequestDto(Math.random() * 100, user1.getUniqueId(), user2.getUniqueId());
        String test1 = "GET /transfer HTTP/1.1\n"
                + "Host: test\n"
                + "Connection: Keep-Alive\n"
                + "\n"
                + gson.toJson(requestDto);
        String response = Server.processRequest(test1);
        String[] responseParts = response.split("\n");
        Assert.assertEquals(responseParts[0], "HTTP/1.1 400 Bad Request");
        RestApiAppResponse<?> userRes = GsonTool.gson.fromJson(responseParts[2],
                new TypeToken<RestApiAppResponse<?>>() {
                }.getType());
        Assert.assertEquals(userRes.message, "Not enough funds.");
    }

    @Test(singleThreaded = true)
    public void transferTest5() {
        UserDao.reset();
        UserDto user1 = TestUtils.createUser();
        UserDto user2 = TestUtils.createUser();

        double randomSeed = Math.random();
        double amountToTransfer = randomSeed * 100;
        double amountToDeposit =  randomSeed * 100 * 2;

        UserDao userDao = UserDao.getInstance();
        user1.setBalance(amountToDeposit);
        userDao.put(user1);

        TransferRequestDto requestDto = new TransferRequestDto(amountToTransfer, user1.getUniqueId(), user2.getUniqueId());
        String test1 = "GET /transfer HTTP/1.1\n"
                + "Host: test\n"
                + "Connection: Keep-Alive\n"
                + "\n"
                + gson.toJson(requestDto);
        String response = Server.processRequest(test1);
        String[] responseParts = response.split("\n");
        Assert.assertEquals(responseParts[0], "HTTP/1.1 200 OK");
        RestApiAppResponse<?> userRes = GsonTool.gson.fromJson(responseParts[2],
                new TypeToken<RestApiAppResponse<?>>() {
                }.getType());
        Assert.assertEquals(userRes.message, null);

        user1 = userDao.get(user1.getUniqueId());
        user2 = userDao.get(user2.getUniqueId());
        Assert.assertEquals(user1.getBalance(), amountToDeposit - amountToTransfer);
        Assert.assertEquals(user2.getBalance(), amountToTransfer);
    }
}

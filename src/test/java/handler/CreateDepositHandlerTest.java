package handler;

import dao.TransactionDao;
import dao.UserDao;
import dto.TransactionDto;
import java.util.List;

import dto.UserDto;
import org.testng.Assert;
import org.testng.annotations.Test;
import server.Server;

public class CreateDepositHandlerTest {

  @Test(singleThreaded = true)
  public void createDepositTest(){
    TransactionDao.reset();
    TransactionDto transactionDto = new TransactionDto();
    transactionDto.setAmount(Math.random());
    transactionDto.setUserId(String.valueOf(Math.random()));

    UserDao.reset();
    UserDao userDao = UserDao.getInstance();
    var user = new UserDto(transactionDto.getUserId());
    userDao.put(user);

    String test1 = "POST /createDeposit HTTP/1.1\n"
        + "Host: test\n"
        + "Connection: Keep-Alive\n"
        + "\n"
        + GsonTool.gson.toJson(transactionDto);
    String response = Server.processRequest(test1);
    String[] responseParts = response.split("\n");
    Assert.assertEquals(responseParts[0], "HTTP/1.1 200 OK");
    List<TransactionDto> transactionDtos = TransactionDao.getInstance().getAll();
    Assert.assertEquals(transactionDtos.size(), 1);
    Assert.assertNotNull(transactionDtos.get(0).getUniqueId());
    Assert.assertEquals(transactionDtos.get(0).getAmount(), transactionDto.getAmount());
    Assert.assertEquals(user.getBalance(), transactionDto.getAmount());
  }



}

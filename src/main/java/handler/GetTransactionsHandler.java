package handler;

import dao.TransactionDao;
import dto.TransactionDto;
import request.ParsedRequest;
import response.CustomHttpResponse;
import response.ResponseBuilder;
import response.RestApiAppResponse;

import java.util.List;
import java.util.stream.Collectors;

public class GetTransactionsHandler implements BaseHandler {

  @Override
  public CustomHttpResponse handleRequest(ParsedRequest request) {
    // TODO
    String userId = request.getQueryParam("userId");

    if (userId == null || userId.isEmpty()) {
      return null;
    }

    List<TransactionDto> allTransactions = TransactionDao.getInstance().getAll();

    // Filter the transactions for the specificed user.
    List<TransactionDto> userTransactions = allTransactions.stream()
            .filter(transaction -> transaction.getUserId().equals(userId))
            .collect(Collectors.toList());

    RestApiAppResponse<TransactionDto> response = new RestApiAppResponse<>(true,
            userTransactions, "Transactions fetched successfully.");


      return new ResponseBuilder()
              .setStatus("200 OK")
              .setBody(GsonTool.gson.toJson(response))
              .build();

  }

}
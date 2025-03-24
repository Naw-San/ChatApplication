package handler;

import dao.TransactionDao;
import dao.UserDao;
import dto.TransactionDto;
import dto.TransactionType;
import dto.UserDto;
import request.ParsedRequest;
import response.CustomHttpResponse;
import response.ResponseBuilder;
import response.RestApiAppResponse;

import java.util.List;
import java.util.UUID;

public class CreateDepositHandler implements BaseHandler {

    @Override
    public CustomHttpResponse handleRequest(ParsedRequest request) {
        // TODO
        TransactionDto transaction = GsonTool.gson.fromJson(request.getBody(), TransactionDto.class);

        if (transaction.getUniqueId() == null || transaction.getUniqueId().isEmpty()) {
            transaction.setUniqueId(UUID.randomUUID().toString());
        }

        if (transaction.getTransactionType() != null && transaction.getTransactionType() != TransactionType.Deposit)
        {
            return new ResponseBuilder()
                    .setStatus("400 Bad Request")
                    .setBody("Invalid transaction type for this endpoint.")
                    .build();
        }


        UserDto user = UserDao.getInstance().get(transaction.getUserId());
        if (user == null) {
            return new ResponseBuilder()
                    .setStatus("404 Not Found")
                    .setBody("User not found.")
                    .build();
        }

        user.setBalance(user.getBalance() + transaction.getAmount());
        UserDao.getInstance().put(user);
        TransactionDao.getInstance().put(transaction);

        RestApiAppResponse<TransactionDto> response = new RestApiAppResponse<>(true, List.of(transaction), "Deposit created successfully.");
        return new ResponseBuilder()
                .setStatus("200 OK")
                .setBody(GsonTool.gson.toJson(response))
                .build();

        /*
        TransactionDto transaction = GsonTool.gson.fromJson(request.getBody(), TransactionDto.class);

        if (transaction.getUniqueId() == null || transaction.getUniqueId().isEmpty()) {
            transaction.setUniqueId(UUID.randomUUID().toString());
        }

        if (transaction.getTransactionType() != null && transaction.getTransactionType() != TransactionType.Deposit)
        {
            return new ResponseBuilder()
                    .setStatus("400 Bad Request")
                    .setBody("Invalid transaction type for this endpoint.")
                    .build();
        }


        UserDto user = UserDao.getInstance().get(transaction.getUserId());
        if (user == null) {
            return new ResponseBuilder()
                    .setStatus("HTTP/1.1 200 OK")// 404 Not Found
                    .setBody("User not found.")
                    .build();
        }

        user.setBalance(user.getBalance() + transaction.getAmount());
        UserDao.getInstance().put(user);
        TransactionDao.getInstance().put(transaction);

        RestApiAppResponse<TransactionDto> response = new RestApiAppResponse<>(true, List.of(transaction),
                "Deposit created successfully.");
        return new ResponseBuilder()
                .setStatus("HTTP/1.1 200 OK") // 200 OK
                .setBody(GsonTool.gson.toJson(response))
                .build();

         **/

    }

}

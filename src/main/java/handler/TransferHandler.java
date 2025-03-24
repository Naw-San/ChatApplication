package handler;

import dao.TransactionDao;
import dao.UserDao;
import dto.*;
import request.ParsedRequest;
import response.CustomHttpResponse;
import response.ResponseBuilder;
import response.RestApiAppResponse;

import java.util.List;

public class TransferHandler implements BaseHandler {

    @Override
    public CustomHttpResponse handleRequest(ParsedRequest request) {
        // TODO

        TransferRequestDto transferRequest = GsonTool.gson.fromJson(request.getBody(), TransferRequestDto.class);
        UserDao userDao = UserDao.getInstance();
        UserDto fromUser = userDao.get(transferRequest.fromId);
        UserDto toUser = userDao.get(transferRequest.toId);

        if (fromUser == null) {
            RestApiAppResponse<BaseDto> errorResponse = new RestApiAppResponse<>(false, null, "Invalid from user.");
            return new ResponseBuilder()
                    .setStatus("400 Bad Request")
                    .setBody(GsonTool.gson.toJson(errorResponse))
                    .build();
        }
        if (toUser == null) {
            RestApiAppResponse<BaseDto> errorResponse = new RestApiAppResponse<>(false, null, "Invalid user to transfer.");
            return new ResponseBuilder()
                    .setStatus("400 Bad Request")
                    .setBody(GsonTool.gson.toJson(errorResponse))
                    .build();
        }
        if (fromUser.getBalance() < transferRequest.amount) {
            RestApiAppResponse<BaseDto> errorResponse = new RestApiAppResponse<>(false, null, "Not enough funds.");
            return new ResponseBuilder()
                    .setStatus("400 Bad Request")
                    .setBody(GsonTool.gson.toJson(errorResponse))
                    .build();
        }

        // Perform the transfer
        fromUser.setBalance(fromUser.getBalance() - transferRequest.amount);
        toUser.setBalance(toUser.getBalance() + transferRequest.amount);

        // Record the transaction
        TransactionDto transaction = new TransactionDto();
        transaction.setUserId(transferRequest.fromId);
        transaction.setToId(transferRequest.toId);
        transaction.setAmount(transferRequest.amount);
        transaction.setTransactionType(TransactionType.Transfer);
        TransactionDao.getInstance().put(transaction);

        RestApiAppResponse<TransactionDto> response = new RestApiAppResponse<>(true, List.of(transaction), null); // Set message to null
        return new ResponseBuilder()
                .setStatus("200 OK")
                .setBody(GsonTool.gson.toJson(response))
                .build();
    }

}
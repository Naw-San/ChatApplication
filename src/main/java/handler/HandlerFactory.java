package handler;

import request.ParsedRequest;

public class HandlerFactory {
  // routes based on the path. Add your custom handlers here
  public static BaseHandler getHandler(ParsedRequest request) {
    // TODO

    switch (request.getPath()) {
      case "/createUser":
        return new CreateUserHandler();
      case "/createDeposit":
        return new CreateDepositHandler();
      case "/getTransactions":
        return new GetTransactionsHandler();
      case "/transfer":
        return new TransferHandler();
      default:
        return null;
    }
  }
}

package handler;

import dao.UserDao;
import dto.UserDto;
import request.ParsedRequest;
import response.CustomHttpResponse;
import response.ResponseBuilder;
import response.RestApiAppResponse;

import java.util.List;
import java.util.UUID;

public class CreateUserHandler implements BaseHandler{

  @Override
  public CustomHttpResponse handleRequest(ParsedRequest request) {
    // TODO

    UserDto user = GsonTool.gson.fromJson(request.getBody(), UserDto.class);

    if (user.getUniqueId() == null) {
      user.setUniqueId(UUID.randomUUID().toString());
    }

    UserDao.getInstance().put(user);

    RestApiAppResponse<UserDto> response = new RestApiAppResponse<>(true,
            List.of(user), "User created successfully");

    return new ResponseBuilder()
            .setStatus("200 OK")
            .setBody(GsonTool.gson.toJson(response))
            .build();
  }
}

package dao;

import dto.UserDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO fill this out
public class UserDao implements BaseDao<UserDto> {

  private static UserDao instance = new UserDao();

  private Map<String, UserDto> userStorage = new HashMap<>();

  public static UserDao getInstance() {
    return instance;
  }


  @Override
  public void put(UserDto messageDto) {
    // TODO fill this out
    userStorage.put(messageDto.getUniqueId(), messageDto);
  }

  @Override
  public UserDto get(String uniqueId) {
    // TODO fill this out
    return userStorage.get(uniqueId);
  }

  // TODO fill this out
  @Override
  public List<UserDto> getAll() {
    // TODO fill this out
    return new ArrayList<>(userStorage.values());
  }

  // only for testing, do not call this method
  public static void reset(){
    instance = new UserDao();
  }
}

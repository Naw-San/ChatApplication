package testutils;

import dao.UserDao;
import dto.UserDto;

public class TestUtils {

    public static String makeRandomString(){
        return String.valueOf(Math.random());
    }

    public static UserDto createUser(){
        UserDao userDao = UserDao.getInstance();
        String user1 = String.valueOf(Math.random());
        String user1Id = String.valueOf(Math.random());
        UserDto user = new UserDto();
        user.setUserName(user1);
        user.setUniqueId(user1Id);
        userDao.put(user);
        return user;
    }

}

package ServiceImpl;


import DTO.User;
import Service.UserService;

import java.util.Random;

public class UserServiceImpl implements UserService {

    @Override
    public User getUserByUserId(Integer id) {
        System.out.println("Client Search for User "+ id);
        User user = User.builder().userAge(18)
                .userName("GG Bond")
                .userID(id)
                .userRole("Man God")
                .build();
        System.out.println(user);
        return user;
    }
}

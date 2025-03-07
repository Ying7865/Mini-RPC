package RemoteService;

import DTO.User;

public interface UserService {
    User getUserByUserId(Integer id);
    Integer insertUser(User user);
    Integer deleteUser(Integer id);
    Integer UpdateUser(User user);
}

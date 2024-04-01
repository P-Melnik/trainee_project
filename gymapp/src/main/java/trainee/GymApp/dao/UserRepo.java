package trainee.GymApp.dao;

import trainee.GymApp.entity.User;

public interface UserRepo extends Repo<User> {

    User findByUserName(String userName);

    int changePassword(String userName, String newPassword);

    boolean checkPassword(String userName, String password);

    int changeStatus(String userName);
}

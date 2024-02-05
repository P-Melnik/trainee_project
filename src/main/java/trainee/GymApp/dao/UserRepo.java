package trainee.GymApp.dao;

import trainee.GymApp.entity.User;

public interface UserRepo extends Repo<User> {

    User findByUserName(String userName);

    void changePassword(String userName, String newPassword);

    public boolean checkPassword(String userName, String password);

    void changeStatus(String userName);
}

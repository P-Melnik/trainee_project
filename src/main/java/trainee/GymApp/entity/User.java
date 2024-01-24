package trainee.GymApp.entity;

import lombok.Data;

@Data
public class User {

    private long userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private boolean isActive;

}

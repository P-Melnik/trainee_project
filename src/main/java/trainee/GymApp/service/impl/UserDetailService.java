package trainee.GymApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import trainee.GymApp.dao.UserRepo;

@Service
public class UserDetailService {

    @Autowired
    private UserRepo userRepo;

    public UserDetailsService userDetailsService() {
        return username -> (UserDetails) userRepo.findByUserName(username);
    }
}

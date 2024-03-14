package trainee.GymApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trainee.GymApp.dao.UserRepo;
import trainee.GymApp.entity.User;

@Service
public class UserDetailService {

    @Autowired
    private UserRepo userRepo;

//    @Transactional
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //todo -> is here Optional<User>.orElseThrow(() -> userNotFoundExc) ??
//        User user = userRepo.findByUserName(username);
//        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword());
//        return
//    }

    public UserDetailsService userDetailsService() {
        return username -> (UserDetails) userRepo.findByUserName(username);
    }
}

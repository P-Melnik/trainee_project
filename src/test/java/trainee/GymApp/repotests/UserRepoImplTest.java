package trainee.GymApp.repotests;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import trainee.GymApp.dao.UserRepo;
import trainee.GymApp.entity.User;
import trainee.GymApp.utils.UserUtil;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
@ActiveProfiles("test")
public class UserRepoImplTest {

    @Autowired
    private UserRepo userRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testCreate() {
        User user = createUser();
        userRepo.create(user);

        User retrievedUser = userRepo.findById(user.getId());
        Assertions.assertNotNull(retrievedUser);
        Assertions.assertEquals(user.getId(), retrievedUser.getId());
    }

    @Test
    public void testUpdate() {
        User user = createUser();
        userRepo.create(user);

        user.setFirstName("UpdatedFirstName");
        user.setLastName("UpdatedLastName");
        user.setUserName(UserUtil.generateLogin("UpdatedFirstName", "UpdatedLastName"));

        userRepo.update(user);

        User updatedUser = userRepo.findById(user.getId());
        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals("UpdatedFirstName", updatedUser.getFirstName());
        Assertions.assertEquals("UpdatedLastName", updatedUser.getLastName());
    }

    @Test
    public void testFindByUserName() {
        User user = createUser();
        userRepo.create(user);

        User retrievedUser = userRepo.findByUserName(user.getUsername());
        Assertions.assertNotNull(retrievedUser);
        Assertions.assertEquals(user.getUsername(), retrievedUser.getUsername());
    }

    @Test
    public void testFindAll() {
        User user1 = createUser();
        User user2 = createUser();
        userRepo.create(user1);
        userRepo.create(user2);

        List<User> userList = userRepo.findAll();
        Assertions.assertNotNull(userList);
        Assertions.assertEquals(2, userList.size());
    }

    @Test
    public void testChangePassword() {
        User user = createUser();
        userRepo.create(user);

        String newPassword = UserUtil.generatePassword();
        userRepo.changePassword(user.getUsername(), newPassword);
        entityManager.refresh(user);
        User updatedUser = userRepo.findById(user.getId());
        Assertions.assertEquals(newPassword, updatedUser.getPassword());
    }

    @Test
    public void testCheckPassword() {
        User user = createUser();
        userRepo.create(user);

        Assertions.assertTrue(userRepo.checkPassword(user.getUsername(), user.getPassword()));
        Assertions.assertFalse(userRepo.checkPassword(user.getUsername(), "wrongPassword"));
    }

    @Test
    public void testChangeStatus() {
        User user = createUser();
        userRepo.create(user);

        userRepo.changeStatus(user.getUsername());
        entityManager.refresh(user);
        System.out.println(user.isActive());
        Assertions.assertNotEquals(user.isActive(), true);
    }

    private User createUser() {
        return new User("pavel", "kotov", UserUtil.generateLogin("pavel", "kotov")
        , UserUtil.generatePassword(), true);
    }
}

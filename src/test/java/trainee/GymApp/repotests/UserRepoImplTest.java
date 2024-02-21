package trainee.GymApp.repotests;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import trainee.GymApp.config.H2TestConfig;
import trainee.GymApp.config.HibernateConfig;
import trainee.GymApp.dao.UserRepo;
import trainee.GymApp.entity.User;
import trainee.GymApp.service.UserUtil;

import java.util.List;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateConfig.class, H2TestConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@ActiveProfiles("test")
public class UserRepoImplTest {

    @Autowired
    private UserRepo userRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void testCreate() {
        User user = createUser();
        userRepo.create(user);

        User retrievedUser = userRepo.findById(user.getId());
        Assertions.assertNotNull(retrievedUser);
        Assertions.assertEquals(user.getId(), retrievedUser.getId());
    }

    @Test
    void testUpdate() {
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
    void testFindByUserName() {
        User user = createUser();
        userRepo.create(user);

        User retrievedUser = userRepo.findByUserName(user.getUserName());
        Assertions.assertNotNull(retrievedUser);
        Assertions.assertEquals(user.getUserName(), retrievedUser.getUserName());
    }

    @Test
    void testFindAll() {
        User user1 = createUser();
        User user2 = createUser();
        userRepo.create(user1);
        userRepo.create(user2);

        List<User> userList = userRepo.findAll();
        Assertions.assertNotNull(userList);
        Assertions.assertEquals(2, userList.size());
    }

    @Test
    void testDelete() {
        User user = createUser();
        userRepo.create(user);

        boolean deleted = userRepo.delete(user.getId());
        Assertions.assertTrue(deleted);

        User deletedUser = userRepo.findById(user.getId());
        Assertions.assertNull(deletedUser);
    }

    @Test
    void testChangePassword() {
        User user = createUser();
        userRepo.create(user);

        String newPassword = UserUtil.generatePassword();
        userRepo.changePassword(user.getUserName(), newPassword);
        entityManager.refresh(user);
        User updatedUser = userRepo.findById(user.getId());
        Assertions.assertEquals(newPassword, updatedUser.getPassword());
    }

    @Test
    void testCheckPassword() {
        User user = createUser();
        userRepo.create(user);

        Assertions.assertTrue(userRepo.checkPassword(user.getUserName(), user.getPassword()));
        Assertions.assertFalse(userRepo.checkPassword(user.getUserName(), "wrongPassword"));
    }

    @Test
    void testChangeStatus() {
        User user = createUser();
        userRepo.create(user);

        userRepo.changeStatus(user.getUserName());
        entityManager.refresh(user);
        System.out.println(user.isActive());
        Assertions.assertNotEquals(user.isActive(), true);
    }

    private User createUser() {
        return new User("pavel", "kotov", UserUtil.generateLogin("pavel", "kotov")
        , UserUtil.generatePassword(), true);
    }
}

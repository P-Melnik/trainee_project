package trainee.GymApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import trainee.GymApp.entity.Entity;
import trainee.GymApp.entity.Trainee;
import trainee.GymApp.storage.Storage;

import java.util.Map;

public class StorageTest {

    @InjectMocks
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
    }

    @Test
    public void testFindById() {
        long id = 1;
        Trainee trainee = new Trainee();
        trainee.setId(id);

        storage.save(trainee);

        Trainee founded = storage.findById(Trainee.class, id);
        Assertions.assertEquals(trainee, founded);
    }

    @Test
    public void testSave() {
        Trainee trainee = new Trainee();
        trainee.setId(1);

        storage.save(trainee);

        Map<String, Entity> map = storage.getStorage();
        Assertions.assertEquals(1, map.size());
        Assertions.assertEquals(trainee, map.get("trainee:1"));
    }


    @Test
    public void testUpdate() {
        long id = 1;
        Trainee trainee = new Trainee();
        trainee.setId(id);
        storage.save(trainee);

        Trainee updated = new Trainee();
        updated.setId(id);
        updated.setAddress("address");
        storage.update(updated);

        Map<String, Entity> map = storage.getStorage();
        Assertions.assertEquals(updated, map.get("trainee:1"));
    }

    @Test
    public void testUpdateNotFound() {
        long id = 1;
        Trainee trainee = new Trainee();
        trainee.setId(id);

        storage.update(trainee);

        Map<String, Entity> map = storage.getStorage();
        Assertions.assertEquals(0, map.size());

    }


    @Test
    public void testDelete() {
        long id = 1;
        Trainee trainee = new Trainee();
        trainee.setId(id);
        storage.save(trainee);

        storage.delete(Trainee.class, id);

        Map<String, Entity> map = storage.getStorage();

        Assertions.assertEquals(0, map.size());
    }

    @Test
    public void testDeleteNotFound() {
        long id = 1;
        Trainee trainee = new Trainee();
        trainee.setId(id);

        storage.save(trainee);

        storage.delete(Trainee.class, 2);

        Map<String, Entity> map = storage.getStorage();
        Assertions.assertEquals(1, map.size());
    }
}

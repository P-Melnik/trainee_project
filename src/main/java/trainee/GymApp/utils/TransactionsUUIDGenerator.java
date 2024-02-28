package trainee.GymApp.utils;

import java.util.UUID;

public class TransactionsUUIDGenerator {

    public TransactionsUUIDGenerator() {

    }

    public static String generate() {
        return UUID.randomUUID().toString();
    }
}

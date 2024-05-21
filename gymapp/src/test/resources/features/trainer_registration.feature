Feature: Create Trainer

  Scenario: Create a new trainer successfully
    Given trainer correct user data
      | firstName | lastName | trainingType |
      | Sergey    | Ivanov   | GYM          |
    When trainer user sends a POST request with correct data
    Then trainer response contains status code 201
    And returns trainer username
    And trainer password of length 10

  Scenario: Verify that correct usernames of trainers are created when the first name and the last name are the same
    Given trainers with same data
      | firstName | lastName | trainingType | id |
      | John      | Doe      | GYM          | 1  |
      | John      | Doe      | RUNNING      | 2  |
    When trainers send POST request
    Then returns trainer first credentials where username in the format firstName.lastName
    And returns trainer second credentials where username in the format firstName.lastName1

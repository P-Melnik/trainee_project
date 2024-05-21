Feature: Create Trainee

  Scenario: Create a new trainee successfully
    Given trainee correct data
      | firstName | lastName |
      | Jack      | Jackson  |
    When user sends a POST request with correct data
    Then response contains status code 201
    And returns username
    And password of length 10

  Scenario: Verify that correct usernames of trainees are created when the first name and the last name are the same
    Given trainees data with same firstname and lastname
      | firstName | lastName |
      | Ivan      | Ivanov   |
      | Ivan      | Ivanov   |
    When user1, user2 send POST request
    Then returns first credentials where username in the format firstName.lastName
    And returns second credentials where username in the format firstName.lastName1

  Scenario: Create a new trainee with empty first name
    Given trainee data with empty first name
      | firstName | lastName |
      |           | Ivanov   |
    When trainee user sends a POST request with the empty first name
    Then trainee response for data with empty first name contains status code 400

  Scenario: Create a new trainee with empty last name
    Given trainee data with empty last name
      | firstName | lastName |
      | Ivan      |          |
    When trainee user sends a POST request with the last first name
    Then trainee response for data with empty last name contains status code 400

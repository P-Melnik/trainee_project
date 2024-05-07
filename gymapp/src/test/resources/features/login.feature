Feature: Login

  Background:
    Given create profile with first name "Alex" and last name "Johnson"

  Scenario: Login with correct credentials
    When user sends a POST request with correct credentials
    Then cookie contain jwtToken
    And status code 200

  Scenario: Login with invalid credentials
    When user sends a POST request with invalid credentials
    Then response returns status code 401

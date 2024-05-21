Feature: Save and Delete training

  Scenario: Save training successfully
    When WorkloadDTO is send to DB
    Then DB finds workload by username

  Scenario: Delete saved workload
    When new WorkloadDTO is send to DB
    And Send workload with actionType delete
    Then found workload by username is empty

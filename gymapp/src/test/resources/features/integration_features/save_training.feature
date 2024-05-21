Feature: Save training and get report

  Background:
    Given create trainee with firstname "Trainee" and lastname "Trainee"
    And create trainer with firstname "Trainer" and lastname "Trainer"
    And login as trainer
    When create a training on date "2024-04-10" with duration "60" and training name "training1" and check token
    Then returns status code 200 after adding training
    And summary service contains a report with training data of "Trainer.Trainer"

  Scenario: Add more trainings for a trainer
    When add a training on date "2024-04-10" with duration "60" and name of "training2" and check token
    Then returns status code 200 after adding a training in the same month
    And the report shows a total duration of 120 in april 2024 for "Trainer.Trainer"



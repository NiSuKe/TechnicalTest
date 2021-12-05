# Tags: test example

Feature: Test borrowing calculator

  Scenario: Test borrowing calculator

    Given Open chrome
    And Navigate to anz
    When User enters valid details
    Then borrowing estimate is displayed correctly
    When User clicks start over button
    Then Form is cleared
    When User does not enter all details
    Then Correct error is displayed
    Then Application should be closed
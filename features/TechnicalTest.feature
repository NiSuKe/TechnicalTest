Feature: Technical Test feature

  Scenario: Realizar una búqueda
    Given Open chrome
    And Navigate to melia
    And Close melia cookies
    When the user inserts the mandatory values in the Hotel browser
      | Destination | CheckIn   | CheckOut   |
      | Valladolid  | 2021/12/8 | 2021/12/18 |
    And the user clicks on 'Buscar' button
    Then Application should be closed

  Scenario: Obtener el número de habitaciones listadas
    Given Open chrome
    When Navigate to melia specific browse
    And Close melia cookies
    Then the number of rooms displayed is '18'
    And Application should be closed
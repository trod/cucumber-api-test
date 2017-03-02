@my-tag
Feature: Manufacturer endpoint
  The main purpose of this feature is to make sure manufacturer endpoint works
  in isolation from other endpoints. Basically this kind of testing is a perfect candidate to be moved to unit level

  Scenario: User gets a list of available manufactures when sends request with valid secret key
    Given User compose manufacturer request with valid secret key
    When user requests /manufacturer endpoint
    Then returned manufacturer response code is 200
    And returned manufacturer response contains following:
      | 157 | Caterham |
      | 060 | Audi     |

  Scenario: User gets a list of available manufactures when sends request with invalid secret key
    When User compose manufacturer request with invalid secret key
    Then returned manufacturer response code is 403

  Scenario: User gets a list of available manufactures when sends request with empty secret key
    When User compose manufacturer request with empty secret key
    Then returned manufacturer response code is 403

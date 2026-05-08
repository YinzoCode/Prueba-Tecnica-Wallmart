Feature: User Login

Background:
    Given the user is on the home page
    And the user clicks on "My Account"
    And the user clicks on "Login"

  @loginSuccessfully
  Scenario: Login successfully with valid credentials and mail
    When the user completes the login form
    | email    | 123@123.com |
    | password | 1234        |
    Then the user should be My Account page

  @loginFailed
  Scenario Outline: Login failed with invalid or null credentials
    When the user completes the login form with '<email>' and '<password>'
    Then the user should see an error message "Warning: No match for E-Mail Address and/or Password."

  Examples:
    | email                 | password        |
    | Hello123@gmail.com    | DontExist123    | 
    |                       |                 | 
    |mail without @gmail.com| 1234            | 
Feature: Trello Boards
  Scenario: Adding new board
    Given I'm trello user
    When I create Board "TestMe123"
    Then Board "TestMe123" is created
Feature: Summing

  Scenario: Summing with zero
    Given calculator is started
    When I add 2 and 0
    Then result is 2

   Scenario Outline: Summing different numbers with zero for result <result>
     Given calculator is started
     When I add <num1> and <num2>
     Then result is <result>
     Examples:
     |num1|num2|result|
     |0   |7   |7     |
     |0   |0   |0     |
     |-3  |0   |-3    |
     |0   |4   |4     |
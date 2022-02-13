package Steps;

import Calculator.Calc;
import Helpers.Boards;
import POJOs.Board;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class StepdDef {
    Calc calc;
    int result;
    Boards boards;
    String boardID;

    @Given("calculator is started")
    public void calculator_is_started() {
        calc = new Calc();
    }

    @When("I add {int} and {int}")
    public void i_add_and(Integer int1, Integer int2) {
        result = calc.sum(int1, int2);
    }

    @Then("result is {int}")
    public void result_is(Integer expectedResult) {
        Assertions.assertEquals(expectedResult, result);
    }


    @Given("I'm trello user")
    public void i_m_trello_user() {
        boards = new Boards();
    }

    @When("I create Board {string}")
    public void i_create_board(String string) {
        boardID = boards.createBoard(string);
    }

    @Then("Board {string} is created")
    public void board_is_created(String string) {
        Assertions.assertEquals(200, boards.getLastResponseStatusCode());
        Assertions.assertEquals(string, boards.getBoardName(boardID));
    }

    
}

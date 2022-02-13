package TrelloTests;

import Helpers.Boards;
import io.restassured.path.json.exception.JsonPathException;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardHelpersTests {
    Boards boards;

    @Test
    public void shouldCreateNewBoardWithGivenName() {
        //given
        String boardName = "Tralala";
        //when
        String boardID = boards.createBoard(boardName);
        //then
        Assertions.assertEquals(boardName, boards.getBoardName(boardID));
    }

    @Test
    public void shouldCoverWholeCRUDTest() {
        String boardName = "First Name";
        String boardSecondName = "Second name";

        String boardID = boards.createBoard(boardName);
        Assertions.assertEquals(boardName, boards.getBoardName(boardID));
        Assertions.assertEquals(HttpStatus.SC_OK, boards.getBoardStatusCode(boardID));

        boards.changeBoardName(boardID, boardSecondName);
        Assertions.assertEquals(boardSecondName, boards.getBoardName(boardID));
        Assertions.assertEquals(HttpStatus.SC_OK, boards.getBoardStatusCode(boardID));

        boards.deleteBoard(boardID);
        Assertions.assertEquals(HttpStatus.SC_NOT_FOUND, boards.getBoardStatusCode(boardID));
    }

    @Test
    public void shouldCoverWholeCRUDTestButBetter() {
        String boardName = "First Name";
        String boardSecondName = "Second name";

        String boardID = boards.createBoard(boardName);
        Assertions.assertEquals(HttpStatus.SC_OK, boards.getLastResponseStatusCode());
        Assertions.assertEquals(boardName, boards.getBoardName(boardID));

        boards.changeBoardName(boardID, boardSecondName);
        Assertions.assertEquals(HttpStatus.SC_OK, boards.getLastResponseStatusCode());
        Assertions.assertEquals(boardSecondName, boards.getBoardName(boardID));

        boards.deleteBoard(boardID);
        Assertions.assertEquals(HttpStatus.SC_OK, boards.getLastResponseStatusCode());
       // try {
            boards.getBoardName(boardID);
        //} catch (JsonPathException e) {
               //tu moze nic nie byc - chcemy tylko, zeby exception nie przelecial wyzej
          //  System.out.println("Ha zlapalem wyjatek!");
        //}  finally {
            Assertions.assertEquals(HttpStatus.SC_NOT_FOUND, boards.getLastResponseStatusCode());
        //}

        //Assertions.assertEquals(HttpStatus.SC_NOT_FOUND,boards.getBoardStatusCode(boardID));
    }


    @BeforeEach
    public void setUp() {
        boards = new Boards();
    }
}

package TrelloTests;

import Helpers.Boards;
import Helpers.BoardsWP;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardHelpersTestsWP {
    BoardsWP boards;

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
//        Assertions.assertEquals(HttpStatus.SC_OK, boards.getBoardStatusCode(boardID));

        boards.changeBoardName(boardID, boardSecondName);
        Assertions.assertEquals(boardSecondName, boards.getBoardName(boardID));
//        Assertions.assertEquals(HttpStatus.SC_OK, boards.getBoardStatusCode(boardID));

        boards.deleteBoard(boardID);
//        Assertions.assertEquals(HttpStatus.SC_NOT_FOUND, boards.getBoardStatusCode(boardID));
    }

    @Test
    public void shouldCoverWholeCRUDTestButBetter() {
        String boardName = "First Name";
        String boardSecondName = "Second name";
        String boardID = boards.createBoard(boardName);
        Assertions.assertEquals(boardName, boards.getBoardName(boardID));
        boards.changeBoardName(boardID, boardSecondName);
        Assertions.assertEquals(boardSecondName, boards.getBoardName(boardID));
        boards.deleteBoard(boardID);
        Assertions.assertNull(boards.getCurrentBoardName());
    }


    @BeforeEach
    public void setUp() {
        boards = new BoardsWP();
    }
}

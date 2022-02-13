package Helpers;

import POJOs.Board;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class BoardsWP {
    private final String baseUri = "https://api.trello.com/1/";
    private final String token = "6f94a562d1cfbb684255f657f6b16f98ace82a6945c4041e966c1eb9cea074df";
    private final String key = "d8b4a9adf21cc384d7643ae35c69beb0";
    private final String boardsAPIpath = "boards/";
    private Board board;

    public BoardsWP() {
        baseURI = this.baseUri;
        requestSpecification = given().queryParam("token", token)
                .queryParam("key", key)
                .contentType(ContentType.JSON);
    }

    public String createBoard(String name) {
        board = new Board(name, "");
        board = given()
                .body(board)
                .post(boardsAPIpath)
                .as(Board.class);
        return board.getId();
    }

    public String getBoardName(String id) {
        board = get(boardsAPIpath + id)
                .as(Board.class);
        return board.getName();
    }

    public void changeBoardName(String id, String name) {
        board = new Board(name, id);
        board = given()
                .body(board)
                .when()
                .put(boardsAPIpath + board.getId())
                .as(Board.class);
    }

    public void deleteBoard(String boardId) {
        board = delete(boardsAPIpath + boardId)
                .as(Board.class);
    }

    public String getCurrentBoardName() {
        return board.getName();
    }

}

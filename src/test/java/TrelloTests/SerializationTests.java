package TrelloTests;

import POJOs.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class SerializationTests extends BaseTest {
    @Test
    public void shouldGetBoardToAnObject(){
        Board konkretnyBoard = when()
                .get("boards/620193c56b427083c2941803")
                .as(Board.class);
        Board drugiBoard = when()
                .get("boards/6207b60636238817145e8844")
                        .as(Board.class);
        System.out.println(konkretnyBoard.toString());
        System.out.println(drugiBoard.toString());
        Assertions.assertEquals("Test Change from code13",konkretnyBoard.getName());
    }

    @Test
    public void shouldCreateBoardFromObject(){
        Board konkretnyBoard = new Board("Wprost z POJOsa","");
        Board nowoUtworzony = given()
                .body(konkretnyBoard)
                .post("boards")
                .as(Board.class);
        System.out.println(konkretnyBoard.toString());
        System.out.println(nowoUtworzony.toString());
        nowoUtworzony.setName("Zmieniony POJOs"); //w obiekcie javowym zmieniona nazwe, ale w api nie
        System.out.println(nowoUtworzony);
        Board poZmianie = given()
                .body(nowoUtworzony)
                .when()
                .put("boards/"+nowoUtworzony.getId())
                .as(Board.class);
        System.out.println(poZmianie);
    }

    @Test
    public void shouldCreateBoardFromObjectOnOnePOJO(){
        Board konkretnyBoard = new Board("Wprost z POJOsa","");
        System.out.println(konkretnyBoard);
        konkretnyBoard = given()
                .body(konkretnyBoard)
                .post("boards")
                .as(Board.class);
        System.out.println(konkretnyBoard.toString());
        konkretnyBoard.setName("Zmieniony POJOs"); //w obiekcie javowym zmieniona nazwe, ale w api nie
        System.out.println(konkretnyBoard);
        konkretnyBoard = given()
                .body(konkretnyBoard)
                .when()
                .put("boards/"+konkretnyBoard.getId())
                .as(Board.class);
        System.out.println(konkretnyBoard);
        konkretnyBoard = delete("boards/"+konkretnyBoard.getId())
                .as(Board.class);
        System.out.println(konkretnyBoard);
        Assertions.assertNull(konkretnyBoard.getName());
    }


}

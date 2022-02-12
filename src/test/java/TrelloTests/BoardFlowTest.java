package TrelloTests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;

import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.*;


public class BoardFlowTest extends BaseTest{
    private String boardID;

    /**
     * Dwa osobne testy na czytanie i zmiane, ale kazdy z nich poprzedzony stworzeniem,
     * a po nim nastepuje skasowanie tablicy
     */


    @AfterEach
    public void shouldDeleteMyBoard() {
         if (boardID == null) throw new RuntimeException("BoardId is null");
        when()
                .delete("boards/" + boardID)
                .then()
                .statusCode(HttpStatus.SC_OK);
        get("boards/" + boardID)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @BeforeEach
    public void shouldCreateBoard() {
        Response response = given()
                .queryParam("name", "Dlugi test")
                .when()
                .post("boards");
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals("Dlugi test", response.getBody().jsonPath().get("name"));
        boardID = response.body().jsonPath().get("id");
    }

    @Test
    public void shouldReadMyBoard() {
        if (boardID == null) throw new RuntimeException("BoardId is null");
        get("boards/" + boardID)
                .then()
                .statusCode(200)
                .body("name", equalTo("Dlugi test"));
    }

    @Test
    public void shouldChangeMyBoard() {
        if (boardID == null) throw new RuntimeException("BoardId is null");
        given()
                .body("{\"name\": \"Test Change from code12\"}")
                .when()
                .put("boards/" + boardID)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo("Test Change from code12"));
        //dopisac potwierdzenie GETem, ze zmiana nastapila
        get("boards/" + boardID)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo("Test Change from code12"));
    }

}

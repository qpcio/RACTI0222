package TrelloTests;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardFlowOrderedTest extends BaseTest{
    private String boardID;

    /**
     * Cztery osobne testy
     * 1. Tworzenie boarda i zapisanie boardID do zmiennej
     * 2. Odczytanie boarda
     * 3. Zmiana nazwy boarda
     * 4. Skasowanie boarda
     *
     * wymaga annotacji na poziomie klasy
     * a) odpowiedzialnej za utrzymanie instacncji klasy dla wszystkich testow - domyslnie kazdy test jest osobna instancja
     * b) odpowiedzialnej za utrzymanie kolejnosci
     *
     * dobrze mi sie wydawalo, ze w jUnit tez sie da! :)
     */


    @Test
    @Order(4)
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

    @Test
    @Order(1)
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
    @Order(2)
    public void shouldReadMyBoard() {
        if (boardID == null) throw new RuntimeException("BoardId is null");
        get("boards/" + boardID)
                .then()
                .statusCode(200)
                .body("name", equalTo("Dlugi test"));
    }

    @Test
    @Order(3)
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

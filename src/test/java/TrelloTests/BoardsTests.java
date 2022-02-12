package TrelloTests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
// import static org.hamcrest.Matchers.equalTo;  ==> tak jest nawet lepiej

public class BoardsTests extends BaseTest{

    @Test
    public void shouldReturnMeAsAMember(){
        given()
                .baseUri("https://api.trello.com/1/")
                .queryParam("token", "6f94a562d1cfbb684255f657f6b16f98ace82a6945c4041e966c1eb9cea074df")
                .queryParam("key", "d8b4a9adf21cc384d7643ae35c69beb0")
                .when()
                .get("members/me")
                .then()
                .statusCode(200)
                .body("fullName",equalTo("Kuba Rosiński"));
    }

    @Test
    public void shouldReturnMeAsAMemberDifferently(){
        Response response =   given()
                .baseUri("https://api.trello.com/1/")
                .queryParam("token", "6f94a562d1cfbb684255f657f6b16f98ace82a6945c4041e966c1eb9cea074df")
                .queryParam("key", "d8b4a9adf21cc384d7643ae35c69beb0")
                .when()
                .get("members/me");
        //response.prettyPrint();
        Assertions.assertEquals(200,response.getStatusCode());
        Assertions.assertEquals("Kuba Rosiński",response.getBody().jsonPath().get("fullName"));
    }

    @Test
    public void shouldReturnMeAsAMemberShorter(){
        Response response = when()
                .get("members/me");
        Assertions.assertEquals(HttpStatus.SC_OK,response.getStatusCode());
        Assertions.assertEquals("Kuba Rosiński",response.getBody().jsonPath().get("fullName"));
    }

    @Test
    public void shouldCreateNewBoard(){
        given()
                .queryParam("name","Zrodzony z kodu")
                .when()
                .post("boards")
                .then()
                .statusCode(200)
                .body("name",equalTo("Zrodzony z kodu"));
    }

    @Test
    public void shouldCreateNewBoardAndCheckThatItExists(){
        Response response = given()
                .queryParam("name","Zrodzony z kodu2")
                .when()
                .post("boards");
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertEquals("Zrodzony z kodu2",response.getBody().jsonPath().get("name"));
        String newBoardID = response.getBody().jsonPath().get("id");
        get("boards/"+newBoardID)
                .then()
                .statusCode(200)
                .body("name",equalTo("Zrodzony z kodu2"));
        //warto sie zdecydowac, z ktorych asercji korzystac i sie ich trzymac
    }

    @Test
    public void shouldChangeNameOfTheBoard(){
          String boardId = "620193c56b427083c2941803";
        Response response = given()
                .body("{\"name\": \"Test Change from code13\"}")
                .when()
                .put("boards/" + boardId);
//                .then()
//                .statusCode(HttpStatus.SC_CREATED)
//                .body("name", equalTo("Test Change from code13"));
        Assertions.assertEquals(HttpStatus.SC_OK,response.getStatusCode(),"Ojojoj nie zmienila sie nazwaa boarda "+boardId);

        //dopisac potwierdzenie GETem, ze zmiana nastapila
        get("boards/" + boardId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo("Test Change from code13"));
    }

    @Test
    public void shouldDeleteBoard(){
        String boardid = "6207b8a6384a5054998ad13c";
        when()
                .delete("boards/"+boardid)
                .then()
                .statusCode(HttpStatus.SC_OK);
        get("boards/"+boardid)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
        
    }

}

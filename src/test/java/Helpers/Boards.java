package Helpers;

import io.restassured.http.ContentType;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class Boards {
    private final String baseUri = "https://api.trello.com/1/";
    private final String token = "6f94a562d1cfbb684255f657f6b16f98ace82a6945c4041e966c1eb9cea074df";
    private final String key = "d8b4a9adf21cc384d7643ae35c69beb0";
    private final String boardsAPIpath = "boards/";
    private Response lastResponse;

    public int getLastResponseStatusCode(){
        return lastResponse.statusCode();
    }

    public String createBoard(String name) {
        lastResponse = given()
                .queryParam("name", name)
                .when()
                .post(boardsAPIpath);

        return lastResponse.body()
                .jsonPath()
                .get("id");
    }

    public String getBoardName(String id) {
        lastResponse =  when()
                .get(boardsAPIpath + id);
        String result;
        try {
            result = lastResponse
                    .body()
                    .jsonPath()
                    .get("name");
        } catch (JsonPathException e){
            result = "";
        }
        return result;
    }

    public void changeBoardName(String id, String newName) {
        lastResponse = given()
                .body("{\"name\": \"" + newName + "\"}")
                .when()
                .put(boardsAPIpath + id);
    }

    public void deleteBoard(String id) {
        lastResponse = delete(boardsAPIpath + id);
    }


    @Deprecated
    public int getBoardStatusCode(String id) {
        return get(boardsAPIpath + id).statusCode();
    }

    public Boards() {
        baseURI = this.baseUri;
        requestSpecification = given().queryParam("token", token)
                .queryParam("key", key)
                .contentType(ContentType.JSON);
    }

}

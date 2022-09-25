import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class SpoonacularTest extends AbstractTest {
    @Test
    void searchHaveNoQuery() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("number", "1")
                .queryParam("offset", "0")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                //.prettyPeek()
                .then()
                .body("number", equalTo(1))
                .body("offset", equalTo(0))
                .body(containsString("title"))
                .body(containsString("id"))
                .body(containsString("image"))
                .body(containsString("imageType"))
                .statusCode(200);
    }

    @Test
    void searchByQueryWord() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("number", "2")
                .queryParam("offset", "0")
                .queryParam("query", "garlic")
                .expect()
                .statusCode(200)
                .body("number", equalTo(2))
                .body("offset", equalTo(0))
                .body(containsString("title"))
                .body(containsString("id"))
                .body(containsString("image"))
                .body(containsString("imageType"))
                .body("results.title", everyItem(containsString("Garlic")))
                .when()
                .get(getBaseUrl() + "recipes/complexSearch");
                //.prettyPeek();
    }

    @Test
    void searchByQueryPhrase() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("number", "3")
                .queryParam("offset", "0")
                .queryParam("query", "baked apple")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                //.prettyPeek()
                .then()
                .statusCode(200)
                .body("number", equalTo(3))
                .body("offset", equalTo(0))
                .body(containsString("title"))
                .body(containsString("id"))
                .body(containsString("image"))
                .body(containsString("imageType"))
                .body("results.title", everyItem(containsString("Baked Apple")));
    }

    @Test
    void searchByCuisine() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("number", "10")
                .queryParam("offset", "0")
                .queryParam("cuisine", "thai")
                .queryParam("addRecipeInformation", true)
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                //.prettyPeek()
                .then()
                .statusCode(200)
                .body("number", equalTo(10))
                .body("offset", equalTo(0))
                .body(containsString("title"))
                .body(containsString("id"))
                .body(containsString("image"))
                .body(containsString("imageType"))
                .body(containsString("Thai"));
    }

    @Test
    void searchRecipeWithInstruction() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("number", "1")
                .queryParam("addRecipeInformation", true)
                .queryParam("instructionsRequired", true)
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                //.prettyPeek()
                .then()
                .statusCode(200)
                .body("number", equalTo(1))
                .body(containsString("title"))
                .body(containsString("id"))
                .body(containsString("image"))
                .body(containsString("imageType"))
                .body(containsString("analyzedInstructions"));
    }

    @Test
    void simpleClassifyCuisine1() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .formParam("title", "sushi")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Japanese"));
    }

    @Test
    void simpleClassifyCuisine2() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .formParam("title", "pizza")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Italian"));
    }

    @Test
    void simpleClassifyCuisine3() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .formParam("title", "Cornish pasty")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("British"));
    }

    @Test
    void simpleClassifyCuisine4() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .formParam("title", "falafel")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Middle Eastern"));
    }

    @Test
    void simpleClassifyCuisine5() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .formParam("title", "burger")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("American"));
    }

    @Test
    void createAndDeletedMealPlan() {

        JsonPath id = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", "8fde41d25053dae85c2ccf08d812fd27d2dbc342")
                .body("{\n"
                        + " \"date\": 1644995179,\n"
                        + " \"slot\": 2,\n"
                        + " \"position\": 2,\n"
                        + " \"type\": \"INGREDIENTS\",\n"
                        + " \"value\": {\n"
                        + " \"ingredients\": [\n"
                        + " {\n"
                        + " \"name\": \"1 orange\"\n"
                        + " }\n"
                        + " ]\n"
                        + " }\n"
                        + "}")
                .when()
                .post(getBaseUrl()+"mealplanner/888921c4-13eb-4bef-ae17-5c8185919615/items")
                .jsonPath();

        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("hash", "8fde41d25053dae85c2ccf08d812fd27d2dbc342")
                .pathParam("id", id.get("id"))
                .when()
                .delete(getBaseUrl() + "mealplanner/888921c4-13eb-4bef-ae17-5c8185919615/items/{id}")
                .body()
                .jsonPath();
        assertThat(response.get("status"), equalTo("success"));
    }


}

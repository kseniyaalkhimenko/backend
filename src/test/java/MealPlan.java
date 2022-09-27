import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MealPlan extends AbstractTest2 {
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

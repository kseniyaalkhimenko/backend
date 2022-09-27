import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PostTest extends AbstractTest2 {
    @Test
    void simpleClassifyCuisine1() {
        JsonPath response = given().spec(getRequestSpecification())
                .formParam("title", "sushi")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .prettyPeek()
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Japanese"));
    }

    @Test
    void simpleClassifyCuisine2() {
        JsonPath response = given().spec(getRequestSpecification())
                .formParam("title", "pizza")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .prettyPeek()
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Mediterranean"));
    }

    @Test
    void simpleClassifyCuisine3() {
        JsonPath response = given().spec(getRequestSpecification())
                .formParam("title", "Cornish pasty")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .prettyPeek()
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("European"));
    }

    @Test
    void simpleClassifyCuisine4() {
        JsonPath response = given().spec(getRequestSpecification())
                .formParam("title", "falafel")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .prettyPeek()
                .body()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("Middle Eastern"));
    }

    @Test
    void simpleClassifyCuisine5() {
        JsonPath response = given().spec(getRequestSpecification())
                .formParam("title", "burger")
                .when()
                .post(getBaseUrl() + "recipes/cuisine")
                .body()
                .prettyPeek()
                .jsonPath();
        assertThat(response.get("cuisine"), equalTo("American"));
    }
}
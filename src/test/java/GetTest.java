import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class GetTest extends AbstractTest {
    @Test
    void searchHaveNoQuery() {
        given().spec(getRequestSpecification())
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .prettyPeek()
                .then()
                .spec(responseSpecification);
    }

    @Test
    void searchByQueryWord() {
        given().spec(getRequestSpecification())
                .queryParam("query", "garlic")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .prettyPeek()
                .then()
                .spec(responseSpecification)
                .body("results.title", everyItem(containsString("Garlic")));
    }

    @Test
    void searchByQueryPhrase() {
        given().spec(getRequestSpecification())
                .queryParam("query", "baked apple")
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .prettyPeek()
                .then()
                .spec(responseSpecification)
                .body("results.title", everyItem(containsString("Baked Apple")));
    }

    @Test
    void searchByCuisine() {
        given().spec(getRequestSpecification())
                .queryParam("cuisine", "thai")
                .queryParam("addRecipeInformation", true)
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .prettyPeek()
                .then()
                .spec(responseSpecification)
                .body(containsString("Thai"));
    }

    @Test
    void searchRecipeWithInstruction() {
        given().spec(getRequestSpecification())
                .queryParam("addRecipeInformation", true)
                .queryParam("instructionsRequired", true)
                .when()
                .get(getBaseUrl() + "recipes/complexSearch")
                .prettyPeek()
                .then()
                .spec(responseSpecification)
                .body(containsString("analyzedInstructions"));
    }
}

package tests

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.bonigarcia.wdm.WebDriverManager
import io.restassured.RestAssured
import net.serenitybdd.junit5.SerenityJUnit5Extension
import net.thucydides.core.steps.ScenarioSteps
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

@ExtendWith(SerenityJUnit5Extension::class)
class BikeLeasingAndCatFactsTest {

    class BikeleasingSteps(private val driver: WebDriver) : ScenarioSteps() {
        fun openBikeleasing() = driver.get("https://www.bikeleasing.de/")
        fun currentUrl() = driver.currentUrl
    }

    private fun createChromeDriver(): WebDriver {
        WebDriverManager.chromedriver().setup()
        return ChromeDriver()
    }

    @Test
    fun testBikeLeasingUrl() {
        val driver = createChromeDriver()
        val steps = BikeleasingSteps(driver)

        try {
            steps.openBikeleasing()
            val url = steps.currentUrl()
            assertEquals("https://bikeleasing.de/", url)
        } finally {
            driver.quit()
        }
    }

    data class CatFact(val fact: String, val length: Int)

    @Test
    fun testCatFactApi() {
        val response = RestAssured.given()
            .baseUri("https://catfact.ninja")
            .param("limit", 5)
            .log().all()
            .get("/facts")
            .then()
            .log().all()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        val mapper = jacksonObjectMapper()
        val rootNode = mapper.readTree(response)
        val factsJson = rootNode["data"].toString()
        val facts: List<CatFact> = mapper.readValue(factsJson)

        assertEquals(5, facts.size)

        val firstFact = facts[0]
        assertEquals(firstFact.length, firstFact.fact.length)
    }
}
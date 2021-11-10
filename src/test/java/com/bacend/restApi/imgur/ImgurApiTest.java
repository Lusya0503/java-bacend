package com.bacend.restApi.imgur;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImgurApiTest {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = ImgurApiParams.API_URL + "/" + ImgurApiParams.API_VERSION;
    }

    @DisplayName("Тест на получение базовой информации об аккаунте")
    @Test
    @Order(1)
    void testAccountBase() {
        String url = "account/" + "facebook526";
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .statusCode(is(200))
                .body("success", is(true))
                .body("status", is(200))
                .body("data.reputation", is(0))
                .log()
                .all()
                .when()
                .get(url);
    }

    @DisplayName("Тест на получение информации о картинке")
    @Test
    @Order(2)
    void testImage() {
        String imageId = "BQWuv4k";
        String url = "image/" + imageId;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .statusCode(is(200))
                .body("success", is(true))
                .body("status", is(200))
                .log()
                .all()
                .when()
                .get(url);
    }

    @DisplayName("Тест на обновление информации о картинке")
    @Test
    @Order(3)
    void testUpdateImageInfo() {
        String imageHash = "zILgfOV";
        String url = "image/" + imageHash;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .formParam("title", "Cat")
                .formParam("description", "Cat")
                .expect()
                .log()
                .all()
                .statusCode(is(200))
                .body("success", is(true))
                .body("data", is(true))
                .when()
                .post(url);

    }

    @DisplayName("Тест на проверку типа картинки")
    @Test
    @Order(4)
    void testDataType() {
        String imageId = "zILgfOV";
        String url = "image/" + imageId;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .body("data.type", is("image/jpeg"))
                .log()
                .all()
                .statusCode(is(200))
                .when()
                .get(url);
    }

    @DisplayName("Тест на проверку описания картинки")
    @Test
    @Order(5)
    void testDataDescription() {
        String imageId = "zILgfOV";
        String url = "image/" + imageId;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .body("data.description", is("Cat"))
                .body("data.title", is("Cat"))
                .log()
                .all()
                .statusCode(is(200))
                .when()
                .get(url);
    }

    @DisplayName("Тест на проверку значения datetime")
    @Test
    @Order(6)
    void testDatetime() {
        String imageId = "zILgfOV";
        String url = "image/" + imageId;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .body("data.datetime", is(1636177427))
                .log()
                .all()
                .statusCode(is(200))
                .when()
                .get(url);
    }


}

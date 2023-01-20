package ru.yandex.praktikum.courier;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

//реализация взаимодействия с API Courier
public class CourierClient {

    //создание курьера (login, password, firstName в courier)
    public static Response create(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    //удаление курьера (id)
    public static Response delete(int id) {
        return given()
                .delete("/api/v1/courier/{id}", id);
    }

    //логин курьера в системе (login, password в courier)
    public static Response login(Courier courier) {
        CourierLogin courierLogin = new CourierLogin(courier);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .when()
                .post("/api/v1/courier/login");

    }

    public static Response login(CourierLogin courierLogin) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .when()
                .post("/api/v1/courier/login");

    }

}

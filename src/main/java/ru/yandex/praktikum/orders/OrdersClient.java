package ru.yandex.praktikum.orders;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

//реализация взаимодействия с Orders
public class OrdersClient {

    //создание заказа
    public static Response create(Order order) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
    }


    //запрос списка заказов
    public static Response ordersListWithoutParams() {
        return given()
                .get("/api/v1/orders");
    }
}

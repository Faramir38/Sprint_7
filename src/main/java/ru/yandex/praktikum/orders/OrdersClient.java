package ru.yandex.praktikum.orders;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.BaseClient;
import ru.yandex.praktikum.SamokatConst;

import static io.restassured.RestAssured.given;

//реализация взаимодействия с Orders
public class OrdersClient extends BaseClient {

    //создание заказа
    @Step ("Создание заказа (POST /api/v1/orders)")
    public static Response createOrder(Order order) {
        return given(requestSpecification)
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(SamokatConst.SAMOKAT_API_ORDERS);
    }


    //запрос списка заказов
    @Step ("Запрос списка заказов (GET /api/v1/orders)")
    public static Response ordersListWithoutParams() {
        return given(requestSpecification)
                .get(SamokatConst.SAMOKAT_API_ORDERS);
    }
}

package ru.yandex.praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.BaseClient;
import ru.yandex.praktikum.SamokatConst;

import static io.restassured.RestAssured.given;

//реализация взаимодействия с API Courier
public class CourierClient extends BaseClient {

    //создание курьера (login, password, firstName в courier)
    @Step ("Создание курьера (POST /api/v1/courier)")
    public static Response createCourier(Courier courier) {
        return given(requestSpecification)
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(SamokatConst.SAMOKAT_API_COURIER_CREATE);
    }

    //удаление курьера (id)
    @Step ("Удаление курьера (DELETE /api/v1/courier/{id})")
    public static Response deleteCourier(int id) {
        return given(requestSpecification)
                .delete(SamokatConst.SAMOKAT_API_COURIER_DELETE, id);
    }

    //логин курьера в системе (login, password в courier)
    @Step ("Авторизация курьера (POST /api/v1/courier/login)")
    public static Response loginCourier(Courier courier) {
        CourierLogin courierLogin = new CourierLogin(courier);
        return given(requestSpecification)
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .when()
                .post(SamokatConst.SAMOKAT_API_COURIER_LOGIN);

    }

    @Step ("Авторизация курьера (POST /api/v1/courier/login)")
    public static Response loginCourier(CourierLogin courierLogin) {
        return given(requestSpecification)
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .when()
                .post(SamokatConst.SAMOKAT_API_COURIER_LOGIN);

    }

}

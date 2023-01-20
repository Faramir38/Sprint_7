package ru.yandex.praktikum.orders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.SamokatConst;


import static org.hamcrest.CoreMatchers.notNullValue;

public class OrdersListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = SamokatConst.SAMOKAT_URL;
    }

//    Проверь, что в тело ответа возвращается список заказов
    @Test
    @DisplayName("GET /api/v1/orders возвращает список заказов")
    public void OrdersListReturnOrdersList() {
        //Arrange
        //Act
        OrdersClient.ordersListWithoutParams().then()
                //Assert
                .statusCode(200).and().assertThat().body("orders", notNullValue());

    }

}

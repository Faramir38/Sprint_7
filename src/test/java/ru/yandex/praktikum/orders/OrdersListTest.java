package ru.yandex.praktikum.orders;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;


import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrdersListTest {

//    Проверь, что в тело ответа возвращается список заказов
    @Test
    @DisplayName("GET /api/v1/orders возвращает список заказов")
    public void OrdersListReturnOrdersList() {
        //Arrange
        //Act
        OrdersClient.ordersListWithoutParams().then()
                //Assert
                .statusCode(SC_OK).and().assertThat().body("orders", notNullValue());

    }

}

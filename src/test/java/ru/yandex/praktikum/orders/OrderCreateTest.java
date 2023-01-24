package ru.yandex.praktikum.orders;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderCreateTest {

    //тело ответа содержит track.
    @Test
    @DisplayName("POST /api/v1/orders возвращает в теле 'track'")
    public void createContainsTrackWhenSuccessful() {
        //Arrange
        Order order = OrderGenerator.random();
        //Act
        OrdersClient.createOrder(order).then()
        //Assert
        .statusCode(SC_CREATED).and().assertThat().body("track", notNullValue());

    }

}

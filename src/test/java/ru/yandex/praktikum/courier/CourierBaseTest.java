package ru.yandex.praktikum.courier;

import io.restassured.response.Response;
import org.junit.After;


public class CourierBaseTest {
    //id курьера для удаления
    private Courier courierToDelete = null;

    public Courier getCourierToDelete() {
        return courierToDelete;
    }

    public void setCourierToDelete(Courier courierToDelete) {
        this.courierToDelete = courierToDelete;
    }

    @After
    public void deleteCourier() {
        if (courierToDelete != null) {
            Response response = CourierClient.loginCourier(getCourierToDelete());
            CourierId id = response.body().as(CourierId.class);
            if (id.getId() > 0) {
                CourierClient.deleteCourier(id.getId());

            }
        }
    }

}

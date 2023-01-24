package ru.yandex.praktikum.orders;

import io.qameta.allure.Step;
import net.datafaker.Faker;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OrderGenerator {
    @Step ("Создание случайных данных заказа")
    public static Order random() {
        Faker faker = new Faker();
        List<String> colorList =
                faker.collection(
                        () -> faker.expression("#{options.option 'BLACK', 'GREY'}"))
                                .len(0,1)
                        .generate();
        return new Order(faker.name().firstName(),
                faker.name().lastName(),
                faker.address().streetAddress(),
                faker.random().nextInt(1, 20).toString(),
                faker.phoneNumber().cellPhone(),
                faker.random().nextInt(1,5),
                faker.date().future(1, TimeUnit.DAYS, "YYYY-MM-dd"),
                faker.text().text(20),
                colorList.toArray(new String[0]));
    }
}

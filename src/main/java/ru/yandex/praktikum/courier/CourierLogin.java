package ru.yandex.praktikum.courier;

//POJO для логина курьера
public class CourierLogin {

    private String login;
    private String password;

    public CourierLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierLogin() {
    }

    public CourierLogin(Courier courier) {
        this.login = courier.getLogin();
        this.password = courier.getPassword();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

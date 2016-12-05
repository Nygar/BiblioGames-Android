package com.bibliogames.nygar.bibliogames.view.interfaces;


import com.bibliogames.nygar.bibliogames.model.User;

public interface LoginActivityInterface {
    void loginFragmentOk(User user);
    void loginBad();
    void goToRegisterUser();
    void backToLogin();
    void loadOn();
    void loadOff();

}

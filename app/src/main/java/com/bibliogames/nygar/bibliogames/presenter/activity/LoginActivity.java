package com.bibliogames.nygar.bibliogames.presenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bibliogames.nygar.bibliogames.R;
import com.bibliogames.nygar.bibliogames.model.User;
import com.bibliogames.nygar.bibliogames.services.ApiRestImpl;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.LoginServiceInterface;
import com.bibliogames.nygar.bibliogames.presenter.fragment.LoginFragment;
import com.bibliogames.nygar.bibliogames.presenter.fragment.RegistrationFragment;
import com.bibliogames.nygar.bibliogames.presenter.interfaces.LoginActivityInterface;
import com.bibliogames.nygar.bibliogames.presenter.utils.CustomSharedPreferences;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
    Esta clase contendra la {@link android.app.Activity} del login, la cual se compone en dos {@link android.support.v4.app.Fragment}
    Una es {@link LoginFragment} y la otra {@link RegistrationFragment}

    En esta clase solo vamos a controlar a que fragment deberia de ir en que momento

 */

public class LoginActivity extends BaseActivity implements LoginActivityInterface,LoginServiceInterface{

    @BindView(R.id.view_loading)
    LinearLayout viewLoading;

    @BindString(R.string.badLogin)
    String badLogin;
    @BindString(R.string.no_network)
    String noNet;

    private CustomSharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        viewLoading.setVisibility(View.VISIBLE);
        preferences = new CustomSharedPreferences(this);
        final LoginServiceInterface loginServiceInterface = this;

        //Si no hay usuario o no existe conexion en sharedPreferences vamos al login
        User currentUser =preferences.getCurrentUser();
        if(currentUser==null){
            loadOff();
            addFragment(R.id.loginFragmentContainer,new LoginFragment());

        }else{
            //Sino comprobamos si el usuario esta en la base de datos
            new ApiRestImpl(loginServiceInterface,this).postLogin(currentUser.getNick(),currentUser.getPass());

        }
    }//Fin oncreate

    @Override
    public void loadOn(){
        viewLoading.setVisibility(View.VISIBLE);
    }
    @Override
    public void loadOff(){
        viewLoading.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        loadOff();
        super.onBackPressed();
    }

    /**
     * Estos son los metodos de la interface {@link LoginActivityInterface}
     * Con estos metodos podemos comunicar los {@link android.support.v4.app.Fragment} con {@link LoginActivity}
     */

    //Este es cuando la comprobacion inicial que hacemos al principio de esta actividad ha tenido exito
    @Override
    public void loginOk(User userLogin) {
        if(userLogin!=null) {
            //Guardamos al usuario in shared preferences
            preferences.setCurrentUser(userLogin);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            loginBad();
        }
    }

    //Este es para cuando se comprueba el login cuando el usuario pulse el boton en el fragment
    @Override
    public void loginFragmentOk(User userLogged) {
        if(userLogged!=null) {
            //Guardamos al usuario in shared preferences
            preferences.setCurrentUser(userLogged);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            loadOff();
            Toast.makeText(this,badLogin,Toast.LENGTH_SHORT).show();
        }
    }

    //Para cuando se produce un error de conexion
    @Override
    public void loginFail() {
        loadOff();
        Toast.makeText(this,noNet,Toast.LENGTH_SHORT).show();
    }


    //Con este metodo mostraremos un mensaje de login no valido
    @Override
    public void loginBad() {
        loadOff();
        Toast.makeText(this,badLogin,Toast.LENGTH_SHORT).show();

        preferences.clearUser();
        addFragment(R.id.loginFragmentContainer,new LoginFragment());
    }


    //Con este metodo iremos al Registro
    @Override
    public void goToRegisterUser() {
        replaceFragmentBackStack(R.id.loginFragmentContainer, new RegistrationFragment());
    }

    //Con este metodo volvemos al login
    @Override
    public void backToLogin() {
        super.onBackPressed();
    }

}//Finclase

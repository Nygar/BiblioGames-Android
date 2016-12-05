package com.bibliogames.nygar.bibliogames.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bibliogames.nygar.bibliogames.R;
import com.bibliogames.nygar.bibliogames.model.User;
import com.bibliogames.nygar.bibliogames.presenter.ApiRestImpl;
import com.bibliogames.nygar.bibliogames.presenter.serviceinterface.LoginServiceInterface;
import com.bibliogames.nygar.bibliogames.view.fragment.LoginFragment;
import com.bibliogames.nygar.bibliogames.view.fragment.RegistrationFragment;
import com.bibliogames.nygar.bibliogames.view.interfaces.LoginActivityInterface;
import com.bibliogames.nygar.bibliogames.view.utils.CustomSharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
    Esta clase contendra la actividad del login, la cual se compone en dos fragments
    Una es la pantalla de login y la otra la de registro

    En esta clase solo vamos a controlar a que fragment deberia de ir en que momento
 */

public class LoginActivity extends BaseActivity implements LoginActivityInterface,LoginServiceInterface{

    @BindView(R.id.view_loading)
    LinearLayout viewLoading;

    private CustomSharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        viewLoading.setVisibility(View.VISIBLE);
        //Comprobamos si existe algun usuario en el shared preferences
        preferences = new CustomSharedPreferences(this);
        final LoginServiceInterface loginServiceInterface = this;

        /**
         * (networkInfo != null && networkInfo.isConnected())
         * es para decir si la informacion de internet es distinta de null (QUE HAY DATOS DE CONEXION)
         * y que si esta conectado (DEVUELVE TRUE SI ESTA CONECTADO)
         */

        //Si no hay usuario o no existe conexion vamos al Fragment de Login
        User currentUser =preferences.getCurrentUser();
        if(currentUser==null){
            loadOff();
            addFragment(R.id.loginFragmentContainer,new LoginFragment());

        }else{
            //Sino comprobamos si el usuario esta en la base de datos
            //Recogemos el Usuario

            //Llamamos a el AsyncTask que llamara al Web Service del Login
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
     *
     * Con estos metodos podemos comunicar los fragments con la actividad
     */

    /*
    Con este metodo iremos a la actividad main cuando el login haya tenido exito
     */
    @Override
    public void loginOk(User userLogin) {
        if(userLogin!=null) {
            //Guardamos al usuario in shared preferences
            preferences.setCurrentUser(userLogin);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            loadOff();
            loginBad();
        }
    }

    @Override
    public void loginFragmentOk(User userLogged) {
        if(userLogged!=null) {
            preferences.setCurrentUser(userLogged);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            loadOff();
            Toast.makeText(this,"Usuario o contraseña incorrectos",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loginFail() {
        loadOff();
        Toast.makeText(this,"Se ha producido un error de conexion",Toast.LENGTH_SHORT).show();
    }

    /*
    Con este metodo mostraremos un mensaje de login no valido
     */
    @Override
    public void loginBad() {
        loadOff();
        Toast.makeText(this,"Usuario o contraseña incorrectos",Toast.LENGTH_SHORT).show();

        preferences.clearUser();
        addFragment(R.id.loginFragmentContainer,new LoginFragment());
    }

    /*
    Con este metodo iremos al fragment del Registro
     */
    @Override
    public void goToRegisterUser() {
        replaceFragmentBackStack(R.id.loginFragmentContainer, new RegistrationFragment());
    }

    /*
    Con este metodo volvemos al login
     */
    @Override
    public void backToLogin() {
        super.onBackPressed();
    }

}//Finclase

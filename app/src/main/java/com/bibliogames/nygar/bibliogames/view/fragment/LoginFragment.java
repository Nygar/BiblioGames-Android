package com.bibliogames.nygar.bibliogames.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bibliogames.nygar.bibliogames.R;
import com.bibliogames.nygar.bibliogames.model.User;
import com.bibliogames.nygar.bibliogames.presenter.ApiRestImpl;
import com.bibliogames.nygar.bibliogames.presenter.serviceinterface.LoginServiceInterface;
import com.bibliogames.nygar.bibliogames.view.interfaces.LoginActivityInterface;
import com.dd.CircularProgressButton;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Esta es la clase del Fragment que tendra el login
 * localizada en {@link com.bibliogames.nygar.bibliogames.view.activity.LoginActivity}
 */

public class LoginFragment extends Fragment implements LoginServiceInterface{

    @BindView(R.id.etUserName)
    EditText nick;
    @BindView(R.id.etPass)
    EditText pass;
    @BindView(R.id.btnSingIn)
    CircularProgressButton loginButton;
    @BindString(R.string.no_network)
    String noNetString;
    @BindString(R.string.badLogin)
    String badLoginString;

    //Propiedades
    private LoginActivityInterface loginInterface;
    private LoginServiceInterface loginServiceInterface;

    //Constructor
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Metodo que se encargara de asignarle la actividad a la interfaz
    @Override
    public void onAttach(Context a) {
        super.onAttach(a);
        loginInterface = (LoginActivityInterface) a;
        loginServiceInterface = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,view);
        return view;
    }//Fin on create view

    //Este es para el boton Login
    @OnClick(R.id.btnSingIn)
    public void loginOnClickListener(){
        //Lamamos a el AsyncLogin
        //loginInterface.loadOn();
        loginButton.setProgress(0);
        loginButton.setProgress(1);
        loginButton.setIndeterminateProgressMode(true);
        new ApiRestImpl(loginServiceInterface,getContext()).postLogin(nick.getText().toString(),pass.getText().toString());

    }

    @OnClick(R.id.btnRegister)
    public void registerOnClickListener(){
        loginInterface.goToRegisterUser();
    }

    //Metodos de la apiRest
    @Override
    public void loginOk(User userLogged) {
        if(userLogged!=null) {
            loginButton.setProgress(100);
            loginInterface.loginFragmentOk(userLogged);
        }else{
            loginButton.setProgress(-1);
        }
    }

    @Override
    public void loginFail() {
        //loginInterface.loadOff();
        loginButton.setProgress(-1);
        Toast.makeText(getContext(),noNetString,Toast.LENGTH_SHORT).show();
    }
    //ApiRest
}//Fin clase

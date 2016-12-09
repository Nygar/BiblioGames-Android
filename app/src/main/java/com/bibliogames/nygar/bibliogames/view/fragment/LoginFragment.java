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
import com.bibliogames.nygar.bibliogames.services.ApiRestImpl;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.LoginServiceInterface;
import com.bibliogames.nygar.bibliogames.view.interfaces.LoginActivityInterface;
import com.dd.CircularProgressButton;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Esta es la clase del Fragment que tendra el login
 * localizada en {@link com.bibliogames.nygar.bibliogames.view.activity.LoginActivity}
 */

public class LoginFragment extends Fragment implements LoginServiceInterface, Validator.ValidationListener{

    @NotEmpty(messageResId = R.string.empty_validation)
    @Length(min = 3, messageResId = R.string.length_validation)
    @BindView(R.id.etUserName)
    EditText nick;

    @NotEmpty(messageResId = R.string.empty_validation)
    @Password(messageResId = R.string.length_pass_validation)
    @BindView(R.id.etPass)
    EditText pass;

    @BindView(R.id.btnSingIn)
    CircularProgressButton loginButton;
    @BindString(R.string.no_network)
    String noNetString;

    //Propiedades
    private LoginActivityInterface loginInterface;
    private LoginServiceInterface loginServiceInterface;
    private Validator validator;

    //Constructor
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context a) {
        super.onAttach(a);
        loginInterface = (LoginActivityInterface) a;
        loginServiceInterface = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,view);
        validator = new Validator(this);
        validator.setValidationListener(this);
        return view;
    }//Fin on create view

    private void loginUser(){
        //loginInterface.loadOn();
        loginButton.setProgress(0);
        loginButton.setProgress(1);
        loginButton.setIndeterminateProgressMode(true);
        new ApiRestImpl(loginServiceInterface,getContext()).postLogin(nick.getText().toString(),pass.getText().toString());
    }

    /**
     * onClickListeners
     */
    @OnClick(R.id.btnSingIn)
    public void loginOnClickListener(){
        validator.validate();
    }

    @OnClick(R.id.btnRegister)
    public void registerOnClickListener(){
        loginInterface.goToRegisterUser();
    }

    /**
     * Respuestas de la api
     * {@link LoginServiceInterface}
     */
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
        loginButton.setProgress(-1);
        Toast.makeText(getContext(),noNetString,Toast.LENGTH_SHORT).show();
    }

    /**
     * Implementacion de {@link Validator.ValidationListener}
     */
    @Override
    public void onValidationSucceeded() {
        loginUser();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            }
        }
    }
}//Fin clase

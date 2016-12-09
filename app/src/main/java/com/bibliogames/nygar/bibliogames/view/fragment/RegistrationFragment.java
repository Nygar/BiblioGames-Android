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
import com.bibliogames.nygar.bibliogames.model.ApiResponse;
import com.bibliogames.nygar.bibliogames.services.ApiRestImpl;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.RegisterServiceInterface;
import com.bibliogames.nygar.bibliogames.view.interfaces.LoginActivityInterface;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
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
 * Esta es la clase del fragment que tendra el registro de los usuarios
 * localizada en {@link com.bibliogames.nygar.bibliogames.view.activity.LoginActivity}
 */
public class RegistrationFragment extends Fragment implements RegisterServiceInterface,Validator.ValidationListener {

    @NotEmpty(messageResId = R.string.empty_validation)
    @Length(min = 3, messageResId = R.string.length_validation)
    @BindView(R.id.etUserNick)
    EditText nick;

    @NotEmpty(messageResId = R.string.empty_validation)
    @Password(messageResId = R.string.length_pass_validation)
    @BindView(R.id.etPass)
    EditText pass;

    @NotEmpty(messageResId = R.string.empty_validation)
    @ConfirmPassword(messageResId = R.string.pass_confirm_validation)
    @BindView(R.id.etCorfim)
    EditText pass2;

    @NotEmpty(messageResId = R.string.empty_validation)
    @Length(min = 3, messageResId = R.string.length_validation)
    @BindView(R.id.etName)
    EditText name;

    @BindString(R.string.no_network)
    String noNetString;
    @BindString(R.string.noSamePass)
    String noSamePassString;

    //Propiedades
    private LoginActivityInterface loginActivityInterface;
    private Validator validator;

    //Constructor
    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context a) {
        super.onAttach(a);
        loginActivityInterface = (LoginActivityInterface) a;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_registration, container, false);
        ButterKnife.bind(this,view);
        validator = new Validator(this);
        validator.setValidationListener(this);

        return view;
    }//Fin oncreate

    /**
     * onClickListener
     */
    @OnClick(R.id.btnRegistro)
    public void registerButtonOnClickListener(){
        validator.validate();
    }

    /**
     * Respuestas de la api
     * {@link RegisterServiceInterface}
     */
    @Override
    public void registerOk(ApiResponse response) {
        loginActivityInterface.loadOff();
        if(response.getStatus()==200) {
            Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
            loginActivityInterface.backToLogin();
        }else{
            Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void registerFail() {
        loginActivityInterface.loadOff();
        Toast.makeText(getContext(),noNetString,Toast.LENGTH_SHORT).show();

    }

    /**
     * Implementacion de {@link Validator.ValidationListener}
     */
    @Override
    public void onValidationSucceeded() {
        loginActivityInterface.loadOn();
        new ApiRestImpl(this, getContext()).postRegister(nick.getText().toString(), pass.getText().toString(), name.getText().toString());
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
}//Finclase

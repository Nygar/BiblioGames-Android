package com.bibliogames.nygar.bibliogames.presenter.fragment;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bibliogames.nygar.bibliogames.R;
import com.bibliogames.nygar.bibliogames.model.ApiResponse;
import com.bibliogames.nygar.bibliogames.model.User;
import com.bibliogames.nygar.bibliogames.services.ApiRestImpl;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.RegisterServiceInterface;
import com.bibliogames.nygar.bibliogames.presenter.interfaces.LoginActivityInterface;
import com.bibliogames.nygar.bibliogames.presenter.utils.BitmapEncode;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Esta es la clase del fragment que tendra el registro de los usuarios
 * localizada en {@link com.bibliogames.nygar.bibliogames.presenter.activity.LoginActivity}
 */
public class RegistrationFragment extends Fragment implements RegisterServiceInterface {

    @BindView(R.id.etUserNick)
    EditText nick;
    @BindView(R.id.etPass)
    EditText pass;
    @BindView(R.id.etCorfim)
    EditText pass2;
    @BindView(R.id.etName)
    EditText name;
    @BindDrawable(R.drawable.ic_defaultuser)
    Drawable d;
    @BindString(R.string.no_network)
    String noNetString;
    @BindString(R.string.noSamePass)
    String noSamePassString;

    //Propiedades
    private LoginActivityInterface loginActivityInterface;

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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_registration, container, false);
        ButterKnife.bind(this,view);

        return view;
    }//Fin oncreate

    /**
     * onClickListener
     */
    @OnClick(R.id.btnRegistro)
    public void registerButtonOnClickListener(){
        //Creamos el usuario
        User user = new User(name.getText().toString(),nick.getText().toString(),pass.getText().toString());

        //Ponemos el Avatar por defecto
        BitmapEncode be = new BitmapEncode();
        user.setAvatar(be.encodeTobase64(((BitmapDrawable)d).getBitmap()));

        //Asynctask
        if(pass.getText().toString().equals(pass2.getText().toString())) {
            loginActivityInterface.loadOn();
            new ApiRestImpl(this, getContext()).postRegister(nick.getText().toString(), pass.getText().toString(), name.getText().toString());
        }else{
            Toast.makeText(getContext(),noSamePassString,Toast.LENGTH_SHORT).show();
        }
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
}//Finclase

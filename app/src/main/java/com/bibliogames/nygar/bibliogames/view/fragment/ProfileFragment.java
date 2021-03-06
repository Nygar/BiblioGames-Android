package com.bibliogames.nygar.bibliogames.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bibliogames.nygar.bibliogames.R;
import com.bibliogames.nygar.bibliogames.model.ApiResponse;
import com.bibliogames.nygar.bibliogames.model.User;
import com.bibliogames.nygar.bibliogames.services.ApiRestImpl;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.UpdateUserServiceInterface;
import com.bibliogames.nygar.bibliogames.view.interfaces.MainActivityInterface;
import com.bibliogames.nygar.bibliogames.view.utils.BitmapEncode;
import com.bibliogames.nygar.bibliogames.view.utils.CustomSharedPreferences;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Clase {@link Fragment} usada para la pantalla de perfil del usuario
 * Esta clase de usa en {@link com.bibliogames.nygar.bibliogames.view.activity.MainActivity}
 */
public class ProfileFragment extends Fragment implements UpdateUserServiceInterface, Validator.ValidationListener {

    @BindView(R.id.iv_profile_avatar)
    CircleImageView ivAvatar;

    @NotEmpty(messageResId = R.string.empty_validation)
    @Length(min = 3, messageResId = R.string.length_validation)
    @BindView(R.id.etUserNick)
    EditText etNick;

    @NotEmpty(messageResId = R.string.empty_validation)
    @Password(messageResId = R.string.length_pass_validation)
    @BindView(R.id.etPass)
    EditText etPass;

    @NotEmpty(messageResId = R.string.empty_validation)
    @Length(min = 3, messageResId = R.string.length_validation)
    @BindView(R.id.etName)
    EditText etName;

    @BindString(R.string.noText)
    String noTittle;
    @BindString(R.string.user_save)
    String userSaved;
    @BindString(R.string.no_image_picked)
    String noImagePicked;
    @BindString(R.string.gallery_pick_error)
    String errorGallery;
    @BindString(R.string.base_url_images)
    String urlImages;
    @BindString(R.string.no_network)
    String noNetString;

    private static final int RESULT_LOAD_IMG = 1;

    private MainActivityInterface mainActivityInterface;

    private User user;
    private User updateUser;

    private Validator validator;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivityInterface = (MainActivityInterface)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
        validator = new Validator(this);
        validator.setValidationListener(this);
        inicializeFragment();
        return view;
    }

    private void inicializeFragment(){
        CustomSharedPreferences preferences = new CustomSharedPreferences(getContext());
        user = preferences.getCurrentUser();

        if(user!=null){
            Picasso.with(getContext()).load(urlImages+"avatar/"+user.getId()+".JPG")
                    .memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.ic_defaultuser).centerInside().fit().into(ivAvatar);
            etNick.setText(user.getNick());
            etPass.setText(user.getPass());
            etName.setText(user.getName());
        }
    }

    private void updateUser(){
        String nick=etNick.getText().toString();
        String pass=etPass.getText().toString();
        String name=etName.getText().toString();

        if(etNick.getText().toString().equals("")){
            nick=noTittle;
        }
        if(etPass.getText().toString().equals("")){
            pass=noTittle;
        }
        if(etName.getText().toString().equals("")){
            name=noTittle;
        }

        updateUser = new User(nick,pass,name);
        BitmapEncode be = new BitmapEncode();
        Drawable avatar = ivAvatar.getDrawable();
        updateUser.setAvatar(be.encodeTobase64(((BitmapDrawable)avatar).getBitmap()));
        if(user!=null) {
            updateUser.setId(user.getId());
        }

        //UpdateUserAsynctask
        mainActivityInterface.loadOn();
        new ApiRestImpl(this,getContext()).postUpdateUser(user.getId(),updateUser.getNick(),updateUser.getPass(),updateUser.getName(),updateUser.getAvatar());
    }

    private void confirmUpdate(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.update_user_dialog_message)
                .setTitle(R.string.confirm);

        builder.setPositiveButton(R.string.ok, (dialog, id) -> {
            updateUser();
            dialog.dismiss();
        });
        builder.setNegativeButton(R.string.cancel, (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * onCLickListeners
     */
    @OnClick(R.id.btnSave)
    public void saveButtonOnClickListener(){
        validator.validate();
    }

    @OnClick(R.id.btnCloseSession)
    public void closeSession(){
        mainActivityInterface.closeSession();
    }

    @OnClick(R.id.iv_profile_avatar)
    public void pickImage(){
        loadImagefromGallery();
    }

    /**
     * Metodos para recoer una imagen desde la galeria
     */
    public void loadImagefromGallery() {

        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();

                Picasso.with(getContext()).load(selectedImage).centerCrop().fit().into(ivAvatar);

            } else {
                Toast.makeText(getActivity(), noImagePicked,
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(),errorGallery , Toast.LENGTH_LONG)
                    .show();
        }

    }//Fin on activity result

    /**
     * Respuestas de la api
     * {@link UpdateUserServiceInterface}
     */
    @Override
    public void updateUserOk(ApiResponse response) {
        mainActivityInterface.loadOff();
        if(response.getStatus()==200){
            Toast.makeText(getActivity(),userSaved,Toast.LENGTH_LONG).show();
            CustomSharedPreferences preferences = new CustomSharedPreferences(getContext());
            preferences.setCurrentUser(updateUser);
        }
    }

    @Override
    public void updateUserFail() {
        mainActivityInterface.loadOff();
        Toast.makeText(getContext(), noNetString, Toast.LENGTH_SHORT).show();
    }

    /**
     * Implementacion de {@link Validator.ValidationListener}
     */
    @Override
    public void onValidationSucceeded() {
        confirmUpdate();
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
}

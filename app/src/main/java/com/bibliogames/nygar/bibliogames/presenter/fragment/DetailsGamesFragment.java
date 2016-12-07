package com.bibliogames.nygar.bibliogames.presenter.fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bibliogames.nygar.bibliogames.R;
import com.bibliogames.nygar.bibliogames.model.ApiResponse;
import com.bibliogames.nygar.bibliogames.model.Console;
import com.bibliogames.nygar.bibliogames.model.Games;
import com.bibliogames.nygar.bibliogames.model.User;
import com.bibliogames.nygar.bibliogames.services.ApiRestImpl;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.AddGameServiceInterface;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.UpdateGameServiceInterface;
import com.bibliogames.nygar.bibliogames.presenter.interfaces.MainActivityInterface;
import com.bibliogames.nygar.bibliogames.presenter.utils.BitmapEncode;
import com.bibliogames.nygar.bibliogames.presenter.utils.CustomSharedPreferences;
import com.bibliogames.nygar.bibliogames.presenter.utils.ParseXML;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Clase {@link Fragment} fragment usado para la pantalla de add/update games
 * Esta clase de usa en {@link com.bibliogames.nygar.bibliogames.presenter.activity.MainActivity}
 */
public class DetailsGamesFragment extends Fragment implements UpdateGameServiceInterface,AddGameServiceInterface {

    @BindView(R.id.image_details_games_cover)
    ImageView ivCover;
    @BindView(R.id.details_games_editTextTitle)
    EditText tvTitle;
    @BindView(R.id.details_games_editTextBuyPrice)
    EditText tvBuyPrice;
    @BindView(R.id.spinner_detailsGames_consoleList)
    MaterialSpinner spinner;
    @BindView(R.id.details_games_editTextDateBuy)
    TextView tvDate;
    @BindString(R.string.noTittle)
    String noTittle;
    @BindString(R.string.no_image_picked)
    String noImagePicked;
    @BindString(R.string.gallery_pick_error)
    String galleryPickError;
    @BindString(R.string.base_url_images)
    String urlImages;
    @BindString(R.string.no_network)
    String noNetString;


    private static final String ARG_GAME = "DETAILS_GAMES";
    private static final int RESULT_LOAD_IMG = 1;

    private Games backupGame;

    private List<Console> categoryConsole;
    private Console selectedConsoleSpinner;

    private Calendar date;

    private MainActivityInterface mainActivityInterface;

    public DetailsGamesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param dGame Parameter 1.
     * @return A new instance of fragment DetailsGamesFragment.
     */
    public static DetailsGamesFragment newInstance(Games dGame) {
        DetailsGamesFragment fragment = new DetailsGamesFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_GAME, dGame);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            backupGame = getArguments().getParcelable(ARG_GAME);
        }else{backupGame = new Games();}
        date= Calendar.getInstance();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivityInterface = (MainActivityInterface)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_games, container, false);
        ButterKnife.bind(this,view);

        inicializeSpinner();
        setTexts();

        return view;
    }

    private void inicializeSpinner(){
        ParseXML parse = new ParseXML();
        categoryConsole= parse.leerXML(getContext());
        List<String> spinnerList = new ArrayList<>();
        for (Console c: categoryConsole) {
            spinnerList.add(c.getName());
        }
        spinner.setItems(spinnerList);
        spinner.setOnItemSelectedListener((view, position, id, item) -> selectedConsoleSpinner = categoryConsole.get(position));
        spinner.setSelectedIndex(0);
        selectedConsoleSpinner= categoryConsole.get(0);
    }

    private void setTexts(){
        if(backupGame!=null){
            Picasso.with(getContext()).load(urlImages+"coverGames/"+backupGame.getId()+".JPG")
                    .memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.default_game).fit().centerInside().into(ivCover);
            tvTitle.setText(backupGame.getTitle());
            tvBuyPrice.setText(String.valueOf( backupGame.getPrice()));
            try {
                date.setTime( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",new Locale("es", "ES")).parse(backupGame.getDateBuy()));
                tvDate.setText(new SimpleDateFormat("dd/MM/yyyy",new Locale("es", "ES")).format(date.getTime()));

            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(backupGame.getConsole()!=null){
                int backupgameconsole=0;
                for(int i=0;i<categoryConsole.size();i++){
                    if(categoryConsole.get(i).getId()==backupGame.getConsole().getId()){
                        backupgameconsole=i;
                    }
                }
                spinner.setSelectedIndex(backupgameconsole);
                selectedConsoleSpinner = categoryConsole.get(backupgameconsole);
            }
        }else{
            Picasso.with(getContext()).load(R.drawable.default_game).fit().centerInside().into(ivCover);
        }

    }

    @OnClick(R.id.button_toolbar_back)
    public void backOnClickListener(){
        mainActivityInterface.onBack();
    }

    @OnClick(R.id.layout_details_games_date)
    public void textDateBuy(){
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        DatePickerDialog datePickerDialog= new DatePickerDialog(getContext(), (view, year, monthOfYear, dayOfMonth) -> {
            tvDate.setText(dayOfMonth+"/"+monthOfYear+"/"+year);

            date.set(year,monthOfYear,dayOfMonth);

        },cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.button_details_games_save)
    public void saveButtonOnClickListener(){
        String title=tvTitle.getText().toString();
        String price=tvBuyPrice.getText().toString();

        if(tvTitle.getText().toString().equals("")){
            title=noTittle;
        }
        if(tvBuyPrice.getText().toString().equals("")){
            price="00";
        }

        BitmapEncode be = new BitmapEncode();
        Drawable avatar = ivCover.getDrawable();

        if(backupGame !=null){
            backupGame.setDateBuy(new SimpleDateFormat("yyyy/MM/dd",new Locale("es", "ES")).format(date.getTime()));
            backupGame.setTitle(title);
            backupGame.setConsole(selectedConsoleSpinner);
            backupGame.setPrice(Float.parseFloat(price));
            backupGame.setCover(ivCover.getDrawable());


            mainActivityInterface.loadOn();
            new ApiRestImpl(this,getContext()).postUpdateGame(backupGame.getId(),backupGame.getTitle(),backupGame.getPrice(),
                    backupGame.getDateBuy(),backupGame.getConsole().getId(),be.encodeTobase64(((BitmapDrawable)avatar).getBitmap()));
        }else{
            backupGame= new Games();
            backupGame.setDateBuy(new SimpleDateFormat("yyyy/MM/dd",new Locale("es", "ES")).format(date.getTime()));
            backupGame.setTitle(title);
            backupGame.setConsole(selectedConsoleSpinner);
            backupGame.setPrice(Float.parseFloat(price));
            backupGame.setCover(ivCover.getDrawable());
            mainActivityInterface.loadOn();
            CustomSharedPreferences preferences = new CustomSharedPreferences(getContext());
            User user= preferences.getCurrentUser();

            if(user!=null) {
                mainActivityInterface.loadOn();
                new ApiRestImpl(this, getContext()).postAddGame(user.getId(), backupGame.getTitle(),
                        backupGame.getPrice(), backupGame.getDateBuy(), backupGame.getConsole().getId(), be.encodeTobase64(((BitmapDrawable)avatar).getBitmap()));
            }
        }

    }

    @OnClick(R.id.details_games_fab)
    public void pickImage(){
        loadImagefromGallery();
    }

    /**
     * Metodos para recoger una imagen desde la galeria
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
                Picasso.with(getContext()).load(selectedImage).fit().centerCrop().into(ivCover);

            } else {
                Toast.makeText(getActivity(), noImagePicked,
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), galleryPickError, Toast.LENGTH_LONG)
                    .show();
        }

    }//Fin on activity result

    @Override
    public void updateGameOk(ApiResponse response) {
        if(response.getStatus()==200){

            mainActivityInterface.reloadGames();
            mainActivityInterface.onBack();
        }
    }

    @Override
    public void updateGameFail() {
        mainActivityInterface.loadOff();
        Toast.makeText(getContext(), noNetString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addGameOk(ApiResponse response) {
        if(response.getStatus()==200){

            mainActivityInterface.reloadGames();
            mainActivityInterface.onBack();
        }
    }

    @Override
    public void addGameFail() {
        mainActivityInterface.loadOff();
        Toast.makeText(getContext(), noNetString, Toast.LENGTH_SHORT).show();
    }
}

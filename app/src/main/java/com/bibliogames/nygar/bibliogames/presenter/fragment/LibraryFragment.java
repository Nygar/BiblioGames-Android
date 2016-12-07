package com.bibliogames.nygar.bibliogames.presenter.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bibliogames.nygar.bibliogames.R;
import com.bibliogames.nygar.bibliogames.model.Console;
import com.bibliogames.nygar.bibliogames.model.Games;
import com.bibliogames.nygar.bibliogames.services.ApiRestImpl;
import com.bibliogames.nygar.bibliogames.services.serviceinterface.DeleteGameServiceInterface;
import com.bibliogames.nygar.bibliogames.presenter.adapter.LibraryAdapter;
import com.bibliogames.nygar.bibliogames.presenter.interfaces.MainActivityInterface;
import com.bibliogames.nygar.bibliogames.presenter.utils.ParseXML;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Clase {@link Fragment} usada para la pantalla de mostras los videojuegos
 * Esta clase de usa en {@link com.bibliogames.nygar.bibliogames.presenter.activity.MainActivity}
 */
public class LibraryFragment extends Fragment implements DeleteGameServiceInterface {

    @BindView(R.id.recycleview_library)
    RecyclerView recyclerView;
    @BindView(R.id.spinner_library_consoleList)
    MaterialSpinner spinner;
    @BindString(R.string.all)
    String allString;
    @BindString(R.string.no_network)
    String noNetString;

    private List<Games> games;
    private List<Console> categoryConsole;

    private LibraryAdapter adapter;

    private MainActivityInterface mainActivityInterface;
    private DeleteGameServiceInterface deleteGameServiceInterface;

    public LibraryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(games==null) {
            games = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        ButterKnife.bind(this,view);

        inicializeFragment();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivityInterface = (MainActivityInterface)context;
        deleteGameServiceInterface = this;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void inicializeFragment(){

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new LibraryAdapter(games);
        adapter.setListener(onItemClickListener);
        recyclerView.setAdapter(adapter);

        getConsoles();
    }

    public void getConsoles(){
        ParseXML parse = new ParseXML();
        categoryConsole= parse.leerXML(getContext());
        //First item
        categoryConsole.add(0,new Console(0,allString));

        List<String> spinnerList = new ArrayList<>();
        for (Console c: categoryConsole) {
            spinnerList.add(c.getName());
        }
        spinner.setItems(spinnerList);
        spinner.setOnItemSelectedListener((view, position, id, item) -> {
            Console selectedConsole = categoryConsole.get(position);

            if(selectedConsole.getId()!=0){
                List<Games> filteredGames = new ArrayList<>();
                for(int i = 0;i<games.size();i++){
                    if(games.get(i).getConsole()!=null && games.get(i).getConsole().equals(selectedConsole)){
                        filteredGames.add(games.get(i));
                    }
                }
                adapter.updateAdapter(filteredGames);
            }else{
                adapter.updateAdapter(games);
            }
        });
    }

    private LibraryAdapter.OnItemClickListener onItemClickListener = new LibraryAdapter.OnItemClickListener(){
        //Funcion para quitar un juego de la lista
        @Override
        public void onDelete(Games sGame) {
            mainActivityInterface.loadOn();
            new ApiRestImpl(deleteGameServiceInterface,getContext()).postDeleteGame(sGame.getId());
        }

        //Funcion para editar un juego de la lista
        @Override
        public void onEdit(Games games) {
            mainActivityInterface.updateGame(games);
        }
    };

    @OnClick(R.id.button_library_add)
    public void addOnClickListener(){
        mainActivityInterface.addNewGame();
    }

    public void updateFragment(List<Games> nListGames){
        games=nListGames;
        adapter.updateAdapter(nListGames);
    }

    @Override
    public void deleteGameOk() {
        mainActivityInterface.reloadGames();
    }

    @Override
    public void deleteGameFail() {
        Toast.makeText(getContext(), noNetString, Toast.LENGTH_SHORT).show();
    }

    /*private List<Games> datatestList(){
        List<Games> testGames = new ArrayList<>();

        testGames.add(new Games(1,"prueba1",60,new Console(1,"Consola de Pruebas1")));
        testGames.add(new Games(2,"prueba2",60,new Console(1,"Consola de Pruebas2")));
        testGames.add(new Games(3,"prueba3",60,new Console(2,"Consola de Pruebas3")));
        testGames.add(new Games(4,"prueba4",60,new Console(2,"Consola de Pruebas4")));
        testGames.add(new Games(11,"prueba15",60,new Console(3,"Consola de Pruebas1")));
        testGames.add(new Games(22,"prueba26",60,new Console(3,"Consola de Pruebas2")));
        testGames.add(new Games(33,"prueba37",60,new Console(4,"Consola de Pruebas3")));
        testGames.add(new Games(44,"prueba48",60,new Console(4,"Consola de Pruebas4")));

        return testGames;
    }*/

}

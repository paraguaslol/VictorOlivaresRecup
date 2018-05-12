package com.example.victor.victorolivaresrec.Usuaris;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.victor.victorolivaresrec.Fragments.DadesUsuari_Fragment;
import com.example.victor.victorolivaresrec.R;

import java.util.ArrayList;
import java.util.List;

public class Activity_Registrar extends AppCompatActivity implements DadesUsuari_Fragment.OnFragmentInteractionListener{
    DadesUsuari_Fragment dadesUsuari_fragment;
    FragmentManager fm;
    FragmentTransaction ft;
    List<DadesUsuari_Fragment> myFragList = new ArrayList<> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__registrar);
        callRegisterFragment();
    }
    public void callRegisterFragment(){
        fm = getFragmentManager();
        fm.popBackStack();
        ft = fm.beginTransaction();
        dadesUsuari_fragment = new DadesUsuari_Fragment();
        myFragList.add(dadesUsuari_fragment);
        ft.add(R.id.myFrame,dadesUsuari_fragment,"");
        ft.addToBackStack("DadesUsuari_Fragment");
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }
}

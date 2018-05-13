package com.example.victor.victorolivaresrec.Usuaris;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.victor.victorolivaresrec.Fragments.DadesUsuari_Fragment;
import com.example.victor.victorolivaresrec.R;

public class Activity_Registrar extends AppCompatActivity implements DadesUsuari_Fragment.OnFragmentInteractionListener{
    DadesUsuari_Fragment dadesUsuari_fragment;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__registrar);
        callRegisterFragment(null);
    }
    public void callRegisterFragment(String uid){
        dadesUsuari_fragment = DadesUsuari_Fragment.newInstance(uid);
        ft = getSupportFragmentManager().beginTransaction().replace(R.id.myFrame,dadesUsuari_fragment);
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }
}

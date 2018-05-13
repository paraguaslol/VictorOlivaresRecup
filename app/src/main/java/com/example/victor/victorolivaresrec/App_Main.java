package com.example.victor.victorolivaresrec;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.victor.victorolivaresrec.Fragments.DadesUsuari_Fragment;
import com.example.victor.victorolivaresrec.Fragments.Llistar_Usuaris;
import com.example.victor.victorolivaresrec.Model.Usuari;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class App_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        Llistar_Usuaris.OnFragmentInteractionListener, DadesUsuari_Fragment.OnFragmentInteractionListener{

    android.support.v4.app.FragmentTransaction ft;
    TextView nomusuarihead, emailhead;
    String nomUsuari;
    String emailUsuari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app__main);
        callLlistarFragment();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        nomusuarihead = (TextView) header.findViewById(R.id.tvHeadUser);
        emailhead = (TextView) header.findViewById(R.id.tvHeadEmail);

       plenaNav();
    }

    public void callLlistarFragment() {
        Llistar_Usuaris llistar_usuaris = new Llistar_Usuaris();
        ft = getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameUsuaris,llistar_usuaris);
        ft.commit();
    }
    public void plenaNav(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = database.getReference("usuaris/"+UID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuari u = dataSnapshot.getValue(Usuari.class);
                nomUsuari=u.getNomusuari();
                emailUsuari=u.getEmail();
                nomusuarihead.setText(nomUsuari);
                emailhead.setText(emailUsuari);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Ha fallat la lectura: " + databaseError.getCode());
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app__main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_llistar) {
            callLlistarFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

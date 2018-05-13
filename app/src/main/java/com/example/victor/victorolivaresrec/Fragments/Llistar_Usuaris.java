package com.example.victor.victorolivaresrec.Fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.victor.victorolivaresrec.Adapters.Usuaris_Adapter;
import com.example.victor.victorolivaresrec.Model.Usuari;
import com.example.victor.victorolivaresrec.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Llistar_Usuaris extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ArrayList<Usuari>totsElsUsuarisArrayList = new ArrayList<Usuari>();
    private RecyclerView recyclerUsuaris;
    private DatabaseReference mDatabase;
    Usuaris_Adapter adapter;

    public Llistar_Usuaris() {
    }

    // TODO: Rename and change types and number of parameters
    public static Llistar_Usuaris newInstance(String param1, String param2) {
        Llistar_Usuaris fragment = new Llistar_Usuaris();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_llistar__usuaris, container, false);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("usuaris");
        mDatabase.keepSynced(true);

        recyclerUsuaris=(RecyclerView)v.findViewById(R.id.recyclerUsuaris);

        plenaUsuaris();

        return v;
    }

    public void plenaUsuaris() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("usuaris");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                totsElsUsuarisArrayList.clear();
                for (DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    Usuari u = datasnapshot.getValue(Usuari.class);
                    totsElsUsuarisArrayList.add(u);
                }
                adapter = new Usuaris_Adapter(totsElsUsuarisArrayList);
                recyclerUsuaris.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        recyclerUsuaris.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

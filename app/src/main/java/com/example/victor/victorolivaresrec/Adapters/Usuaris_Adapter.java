package com.example.victor.victorolivaresrec.Adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.example.victor.victorolivaresrec.App_Main;
import com.example.victor.victorolivaresrec.Fragments.DadesUsuari_Fragment;
import com.example.victor.victorolivaresrec.Fragments.Llistar_Usuaris;
import com.example.victor.victorolivaresrec.Model.Usuari;
import com.example.victor.victorolivaresrec.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Usuaris_Adapter extends RecyclerView.Adapter<Usuaris_Adapter.UsuariViewHolder> implements View.OnClickListener {
    private List<Usuari> items;
    private View.OnClickListener listener;
    public Usuaris_Adapter(List<Usuari> items) {
        this.items = items;
    }
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public Usuaris_Adapter.UsuariViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_usuari, viewGroup, false);
        v.setOnClickListener(this);
        return new Usuaris_Adapter.UsuariViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Usuaris_Adapter.UsuariViewHolder viewHolder, int i) {
        Usuari item = items.get(i);
        viewHolder.bindUsuari(item);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class UsuariViewHolder extends RecyclerView.ViewHolder {

        public TextView tvIdUsuari, tvNomUsuari, tvCognomsUsuari;
        public ImageButton imgbtnedit, imgbtnborrar;

        public UsuariViewHolder(View v) {
            super(v);
            tvIdUsuari = (TextView) v.findViewById(R.id.tvIdUsuari);
            tvNomUsuari = (TextView) v.findViewById(R.id.tvNomUsuari);
            tvCognomsUsuari = (TextView) v.findViewById(R.id.tvCognomsUsuari);
            imgbtnedit = (ImageButton) v.findViewById(R.id.imgbtnEditarUsuari);
            imgbtnborrar = (ImageButton) v.findViewById(R.id.imgbtnEliminarUsuari);
            imgbtnedit = (ImageButton) v.findViewById(R.id.imgbtnEditarUsuari);
        }

        public void bindUsuari(final Usuari item) {
            final String uid=item.getAuth();
            tvIdUsuari.setText(item.getAuth());
            tvNomUsuari.setText(item.getNom());
            tvCognomsUsuari.setText(item.getCognoms());
            imgbtnborrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                    builder.setTitle("ATENCIÓ");
                    builder.setMessage("Segur que vols eliminar l'element de la llista?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("usuaris");
                                    Query q = ref2.orderByChild("auth").equalTo(uid);
                                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                                                String uidEliminar = datasnapshot.getKey();
                                                DatabaseReference ref = ref2.child(uidEliminar);
                                                ref.removeValue();
                                                items.remove(getAdapterPosition());
                                                //Usuaris_Adapter.notifyDataSetChanged();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            System.out.println("Ha fallat eliminar: " + databaseError.getCode());
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
            imgbtnedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App_Main app_main = (App_Main) v.getContext();
                    DadesUsuari_Fragment dadesUsuari_fragment = DadesUsuari_Fragment.newInstance(item.getAuth());
                    app_main.getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameUsuaris, dadesUsuari_fragment).commit();
                }
            });


        }
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

}

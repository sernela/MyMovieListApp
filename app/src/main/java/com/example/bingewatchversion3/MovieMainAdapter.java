package com.example.bingewatchversion3;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class MovieMainAdapter extends FirebaseRecyclerAdapter<MovieMainModel, MovieMainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MovieMainAdapter(@NonNull FirebaseRecyclerOptions<MovieMainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull MovieMainModel moviemodel) {

        holder.name.setText(moviemodel.getName());
        holder.lastseen.setText(moviemodel.getLastseen());


        Picasso.with(holder.imagesec.getContext()).load(moviemodel.getTurl()).fit().centerCrop()
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.imagesec);


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.imagesec.getContext())
                        .setContentHolder(new ViewHolder(R.layout.movie_update_popup))
                        .setExpanded(true, 1200)
                        .create();

                View view = dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.txtName2);
                EditText lastseen = view.findViewById(R.id.txtlastSeen2);
                EditText turl = view.findViewById(R.id.txtImageUrl2);

                Button btnUpdateM = view.findViewById(R.id.btnUpdatemovie);



                name.setText(moviemodel.getName());
                lastseen.setText(moviemodel.getLastseen());
                turl.setText(moviemodel.getTurl());

                dialogPlus.show();

                btnUpdateM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("lastseen", lastseen.getText().toString());
                        map.put("turl", turl.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Movies")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(), "Data Updated Successfully.", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.name.getContext(), "Error While Updating.", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();

                                    }
                                });
                    }
                });



            }
        });



        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you Sure?");
                builder.setMessage("Deleted data can't be Undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase.getInstance().getReference().child("Movies")
                                .child(getRef(position).getKey()).removeValue();


                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewsec = LayoutInflater.from(parent.getContext()).inflate(R.layout.sec_main_item, parent, false);

        return new myViewHolder(viewsec);
    }

    class myViewHolder extends RecyclerView.ViewHolder{


        ImageView imagesec;
        Button btnEdit, btnDelete;
        TextView name, lastseen;


        public myViewHolder(View view) {
            super(view);

            imagesec = (ImageView) itemView.findViewById(R.id.imageView2);
            name = (TextView) itemView.findViewById(R.id.namemovie2);
            lastseen = (TextView) itemView.findViewById(R.id.lastseentext2);

            btnEdit = (Button)itemView.findViewById(R.id.btnEdit2);
            btnDelete = (Button)itemView.findViewById(R.id.btnDelete2);

        }
    }
}
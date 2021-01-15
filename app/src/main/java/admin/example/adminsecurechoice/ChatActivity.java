package admin.example.adminsecurechoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference RootRef,mRef;
    private ProgressDialog progressDialog;
    private EditText editText;
    private ImageView submit;

    private String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        user_id = getIntent().getExtras().get("USERID").toString();

        RootRef= FirebaseDatabase.getInstance ().getReference ();
        mRef= FirebaseDatabase.getInstance ().getReference ().child("Users").child(user_id).child("Message");
        progressDialog = new ProgressDialog(this);
        progressDialog.setContentView ( R.layout.loading );
        editText = findViewById(R.id.user_message_text);
        submit = findViewById(R.id.user_message_submit);
        progressDialog.setTitle ( "Please Wait..." );
        progressDialog.setCanceledOnTouchOutside ( false );
        progressDialog.setMessage ( "Tips: Please Check your Internet or Wi-fi Connection" );

        recyclerView = findViewById(R.id.Message_Recyler_View);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkUploadaMessage();
            }
        });
    }




    private void OkUploadaMessage() {


        progressDialog.show();

        String Messgaes = editText.getText().toString();
        if (TextUtils.isEmpty ( Messgaes))
        {
            Toast.makeText ( ChatActivity.this, "Enter Something Message..", Toast.LENGTH_SHORT ).show ();
            progressDialog.dismiss();
        }
        else
        {
            String key = RootRef.child("Users").child(user_id).child("Message").push().getKey();
            String key2 = "tyyrtrtyu767b7yb7bb";
            Map<String,Object> updatee = new HashMap<>();
            updatee.put("Text",Messgaes);
            updatee.put("Sender",key2);
            RootRef.child("Users").child(user_id).child("Message").child(key).updateChildren(updatee).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        editText.setText(null);
                        Toast.makeText(ChatActivity.this, "Sent..", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(ChatActivity.this, "Error ! Send Again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }







    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.show();
        FirebaseRecyclerOptions<Contact> optionss =
                new FirebaseRecyclerOptions.Builder<Contact> ()
                        .setQuery(mRef,Contact.class)
                        .build ();


        FirebaseRecyclerAdapter<Contact, StudentViewHolderP> adapter =
                new FirebaseRecyclerAdapter<Contact, StudentViewHolderP> (optionss) {
                    @Override
                    protected void onBindViewHolder(@NonNull final StudentViewHolderP holder, final int position, @NonNull final Contact model) {


                        String cheakstring = model.getSender();

                        if (cheakstring.equals(user_id))
                        {
                            holder.receive.setVisibility(View.GONE);
                            holder.imageView.setVisibility(View.GONE);
                            holder.send.setText(model.getText());
                            progressDialog.dismiss();
                        }
                        else {
                            holder.send.setVisibility(View.GONE);
                            holder.receive.setText(model.getText());
                            progressDialog.dismiss();
                        }





                    }

                    @NonNull
                    @Override
                    public StudentViewHolderP onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                        View view  = LayoutInflater.from ( viewGroup.getContext () ).inflate ( R.layout.message_layout,viewGroup,false );
                        StudentViewHolderP viewHolder  = new StudentViewHolderP(  view);
                        return viewHolder;

                    }
                };

        recyclerView.setAdapter ( adapter );

        adapter.startListening ();
    }

    public static class StudentViewHolderP extends  RecyclerView.ViewHolder
    {

        TextView send,receive;
        ImageView imageView;
        public StudentViewHolderP(@NonNull View itemView) {
            super ( itemView );
            send = itemView.findViewById ( R.id.sender_message_text );
            receive = itemView.findViewById ( R.id.recever_message_text);
            imageView = itemView.findViewById(R.id.messege_profle_images);

        }
    }


}
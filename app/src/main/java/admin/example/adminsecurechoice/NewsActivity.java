package admin.example.adminsecurechoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class NewsActivity extends AppCompatActivity {

    private DatabaseReference RootRef;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private EditText news,numbercount;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);




        RootRef = FirebaseDatabase.getInstance ().getReference ().child ( "Notifications" );

        news = findViewById(R.id.news_edittext);
        numbercount  =  findViewById(R.id.news_edittext_number);
        button = findViewById(R.id.news_edittext_button);

        progressDialog = new ProgressDialog( this);
        progressDialog.setContentView ( R.layout.loading );
        progressDialog.setTitle ( "Please Wait..." );
        progressDialog.setCanceledOnTouchOutside ( false );
        progressDialog.setMessage ( "Tips: Please Cheak your Internet or Wi-fi Connection" );
        recyclerView =findViewById(R.id.Notifications_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UpdatetheNews();
            }
        });
    }

    private void UpdatetheNews() {

        String stringnews = news.getText().toString();
        String stringnumbre = numbercount.getText().toString();


        Map<String,Object> update = new HashMap<>();
        update.put("Name",stringnews);

        RootRef.child(stringnumbre).updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(NewsActivity.this, "Done !", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(NewsActivity.this, "Error ! ", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }

    @Override
    public void onStart() {
        super.onStart ();

        progressDialog.show ();


        FirebaseRecyclerOptions<News> options =
                new FirebaseRecyclerOptions.Builder<News> ()
                        .setQuery(RootRef,News.class)
                        .build ();


        FirebaseRecyclerAdapter<News, StudentViewHolder2> adapter =
                new FirebaseRecyclerAdapter<News, StudentViewHolder2> (options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final StudentViewHolder2 holder, final int position, @NonNull final News model) {

                        String stringg = getRef(position).getKey();
                        holder.name.setText ( model.getName() );
                        holder.number.setText(stringg);


                        progressDialog.dismiss ();




                    }

                    @NonNull
                    @Override
                    public StudentViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                        View view  = LayoutInflater.from ( viewGroup.getContext () ).inflate ( R.layout.news_layout,viewGroup,false );
                        StudentViewHolder2 viewHolder  = new StudentViewHolder2(  view);
                        return viewHolder;

                    }
                };
        recyclerView.setAdapter ( adapter );
        adapter.startListening ();




    }


    public static class StudentViewHolder2 extends  RecyclerView.ViewHolder
    {

        TextView name,number;
        public StudentViewHolder2(@NonNull View itemView) {
            super ( itemView );
            name = itemView.findViewById ( R.id.notifications_news );
            number = itemView.findViewById ( R.id.notifications_number );

        }
    }
}
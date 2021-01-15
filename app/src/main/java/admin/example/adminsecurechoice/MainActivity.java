package admin.example.adminsecurechoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void USERSBUTTON(View view) {
        Intent intentusers = new Intent(MainActivity.this,UsersActivity.class);
        startActivity(intentusers);
    }

    public void NEWSBUTTON(View view) {
        Intent intentnews = new Intent(MainActivity.this,NewsActivity.class);
        startActivity(intentnews);
    }

    public void SUPPORTBUTTON(View view) {
        Intent intentsupport= new Intent(MainActivity.this,SupportActivity.class);
        startActivity(intentsupport);
    }
}
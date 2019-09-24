package creo.com.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {
    private RecyclerView recyclerView;
    Context mContext=this;
    private RelativeLayout rl,rlt;
    ScrollView scrollView;
    TextView edit;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final ArrayList<NotificationPojo> pojo = new ArrayList<>();
        NotificationPojo notificationPojo = new NotificationPojo("You have recieved 5 new compliments",R.drawable.star);
        NotificationPojo notificationPojo1=new NotificationPojo("You have recieved a new policy update",R.drawable.notification);
        pojo.add(notificationPojo);
        pojo.add(notificationPojo1);

        recyclerView = findViewById(R.id.rer);
        edit=findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, EditProfile.class));
                Animatoo.animateSwipeRight(Profile.this);
            }
        });
        scrollView=findViewById(R.id.scroll);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(scrollView.FOCUS_UP);
            }
        });
        rl=findViewById(R.id.rl);
        rlt=findViewById(R.id.rlt);
        rlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Review.class));
                Animatoo.animateFade(Profile.this);

            }
        });
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Earning.class));
                Animatoo.animateSlideUp(Profile.this);
            }
        });
        recyclerView.setNestedScrollingEnabled(false);
        NotificationAdapter notificationAdapter = new NotificationAdapter(pojo,mContext);

        recyclerView.setAdapter(notificationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));



    }
}

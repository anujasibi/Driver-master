package creo.com.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import creo.com.driver.utils.SessionManager;

public class vehicledocument extends AppCompatActivity {

    CardView cardView;
    String make=null;
    String model=null;
    String year=null;
    String color=null;
    String plate=null;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicledocument);
        sessionManager = new SessionManager(this);

        make=sessionManager.getMake();
        model=sessionManager.getModel();
        year=sessionManager.getYear();
        color=sessionManager.getColor();
        plate=sessionManager.getPlate();

        cardView=findViewById(R.id.vechile);


        if(make.equals("")||model.equals("")||year.equals("")||color.equals("")||plate.equals("")){
            cardView.setVisibility(View.VISIBLE);
        }else{
            cardView.setVisibility(View.GONE);
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(vehicledocument.this,vehicledetails.class));
            }
        });
    }
}

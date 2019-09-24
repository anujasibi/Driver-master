package creo.com.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import creo.com.driver.utils.Global;

public class Login extends AppCompatActivity {
    TextView textView,forgot;
    TextInputEditText phoneno,password;
    Context context=this;
    String phone_no = null;

    private String URLline = Global.BASE_URL+"driver/driver_login/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textView=findViewById(R.id.login);
        phoneno=findViewById(R.id.name);
        password=findViewById(R.id.namee);
        forgot=findViewById(R.id.forgot);

        phoneno.setError("Enter your registered number");
        password.setError("Enter your password");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                */
                loginuser();

            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
            }
        });
    }

    private void loginuser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String token=jsonObject.optString("token");
                            String status=jsonObject.optString("status");
                            Log.d("otp","mm"+ot);
                            Log.d("token","mm"+token);
                            if(status.equals("200")){
                                Toast.makeText(Login.this, ot, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Login.this, MainUI.class);
                                intent.putExtra("token", token);
                                Log.d("pppppp","mm"+token);
                                startActivity(intent);
                                Animatoo.animateSlideLeft(Login.this);
                            }
                            else{
                                Toast.makeText(Login.this, ot, Toast.LENGTH_LONG).show();


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response","hhh"+response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("phone_no",phoneno.getText().toString());
                params.put("password",password.getText().toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

}

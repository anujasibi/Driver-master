package creo.com.driver;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import creo.com.driver.utils.Global;
import creo.com.driver.utils.SessionManager;

public class Login extends AppCompatActivity {
    TextView textView,forgot;
    TextInputEditText phoneno,password;
    Context context=this;
    String phone_no = null;
    SessionManager sessionManager;
    private ProgressDialog dialog ;
    private String token;

    private String URLline = Global.BASE_URL+"driver/driver_login/";
    private String URLlinenew = Global.BASE_URL+"driver/check_driver_proof/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(this);
        textView=findViewById(R.id.login);
        phoneno=findViewById(R.id.name);
        password=findViewById(R.id.namee);
        forgot=findViewById(R.id.forgot);
        dialog=new ProgressDialog(Login.this,R.style.MyAlertDialogStyle);



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(phoneno.getText().toString().equals("")){
                    phoneno.setError("Enter your registered number");

                }
                else if(password.getText().toString().equals(""))
                {
                    password.setError("Enter your password");
                }
                else if(phoneno.getText().toString().equals("")&&password.getText().toString().equals(""))
                {
                    Toast.makeText(Login.this,"All fields are required",Toast.LENGTH_SHORT).show();

                }
                else {
                    dialog.setMessage("Loading..");
                    dialog.show();
                    loginuser();

                }


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
                            token=jsonObject.optString("token");
                            Global.user_token = token;
                            String status=jsonObject.optString("status");
                            Log.d("otp","mm"+ot);
                            Log.d("token","mm"+token);
                            if(status.equals("200")){
                                Toast.makeText(Login.this, ot, Toast.LENGTH_LONG).show();
                                checkdoc();
                               /* */
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
    private void checkdoc(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLlinenew,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                    //    Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String ot = jsonObject.optString("message");
                            String status=jsonObject.optString("code");
                            String data=jsonObject.optString("data");
                            String approval=jsonObject.optString("approval");
                            JSONArray jsonObject1 = new JSONArray(data);
                            JSONObject jsonObject2 = jsonObject1.optJSONObject(0);
                            JSONObject fields = jsonObject2.optJSONObject("fields");
                            final String license=fields.optString("license");
                            final String address_proof=fields.optString("address_proof");
                            final String pcc=fields.optString("pcc");
                            sessionManager.setUser_pcc(pcc);
                            sessionManager.setId(address_proof);
                            sessionManager.setLicence(license);
                            Log.d("data","mm"+data);
                            Log.d("approval",",mm"+approval);
                            Log.d("json","mm"+jsonObject1);
                            Log.d("fe","mm"+fields);
                            Log.d("license","mm"+license);
                            Log.d("address","mm"+address_proof);
                            Log.d("pcc","mm"+sessionManager.getUser_pcc());

                            if(status.equals("200")){

                                if(license.equals("")||address_proof.equals("")||pcc.equals("")){
                                  //  sessionManager.setUser_pcc("");
                                    Toast.makeText(Login.this, ot, Toast.LENGTH_LONG).show();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                                    builder.setMessage("Please Upload your documents to continue")

                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    Intent intent = new Intent(Login.this, Documents.class);
                                                    intent.putExtra("pcc", pcc);
                                                    intent.putExtra("license",license);
                                                    intent.putExtra("address_proof",address_proof);
                                                  //  Log.d("pppppp","mm"+token);
                                                    startActivity(intent);
                                                    Animatoo.animateSlideLeft(Login.this);
                                                    //  MyActivity.this.finish();
                                                }
                                            });


                                    AlertDialog alert = builder.create();
alert.show();
                                }
                            }
                            if(!(license.equals("")||address_proof.equals("")||pcc.equals(""))){
                                if(approval.equals("not verified")){
                                    Toast.makeText(Login.this,"Please wait",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Login.this, Approval.class);
                                    startActivity(intent);
                                }
                                else {
                                    Intent intent = new Intent(Login.this, MainUI.class);
                                    intent.putExtra("token", token);
                                    Log.d("pppppp", "mm" + token);
                                    startActivity(intent);
                                    Animatoo.animateSlideLeft(Login.this);
                                }



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
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token "+token);
                return headers;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);


    }

}

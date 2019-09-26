package creo.com.driver.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {


    private SharedPreferences sharedPreferences;


    public SessionManager(Context  context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getUser_pcc() {
        String pcc = sharedPreferences.getString("pcc","");

        return pcc;
    }

    public void setUser_pcc(String user_pcc) {
    sharedPreferences.edit().putString("pcc",user_pcc).commit();
    }

    public void setId(String id){
        sharedPreferences.edit().putString("id",id).commit();
    }

    public String getId(){
        return  sharedPreferences.getString("id","");
    }
    public void setLicence(String lic){
        sharedPreferences.edit().putString("license",lic).commit();
    }
    public String getLicense(){
        return  sharedPreferences.getString("license","");
    }


}

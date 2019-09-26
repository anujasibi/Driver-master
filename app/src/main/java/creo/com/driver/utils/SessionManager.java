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

    public void setMake(String make){
        sharedPreferences.edit().putString("make",make).commit();
    }
    public String getMake(){

            return  sharedPreferences.getString("make","");
    }
    public void setModel(String model){
        sharedPreferences.edit().putString("model",model).commit();
    }
    public String getModel(){

        return  sharedPreferences.getString("model","");
    }
    public void setYear(String year){
        sharedPreferences.edit().putString("year",year).commit();
    }
    public String getYear(){

        return  sharedPreferences.getString("year","");
    }
    public void setPlate(String plate){
        sharedPreferences.edit().putString("plate",plate).commit();
    }
    public String getPlate(){

        return  sharedPreferences.getString("plate","");
    }
    public void setColor(String color){
        sharedPreferences.edit().putString("color",color).commit();
    }
    public String getColor(){

        return  sharedPreferences.getString("color","");
    }


}

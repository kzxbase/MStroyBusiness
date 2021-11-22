package kz.xbase.mstroy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class SessionManager {

    private static final String FILENAME = "mstroybusiness";
    private static final String IS_LOGGED = "IS_LOGGED";
    private static final String PIN = "PIN";
    private static final String PHONE="PHONE";
    private static final String BALANCE="BALANCE";
    private static final String TOKEN="TOKEN";
    private static final String REFRESHTOKEN="REFRESHTOKEN";
    private static final String INN="INN";
    private static final String FIRSTNAME="FIRSTNAME";
    private static final String SECONDNAME="SECONDNAME";
    private static final String LASTNAME="LASTNAME";
    private static final String BIRTHDAY="BIRTHDAY";
    private static final String PASSPORTFRONT="PASSPORTFRONT";
    private static final String PASSPORTBACK="PASSPORTBACK";
    private static final String CITYID="CITYID";
    private static final String CITYLIST="CITYLIST";
    private static final String AVA="AVA";
    private static final String USE_BIOMETRY="USE_BIOMETRY";
    private static final String NOTIFICATIONS_LOAN="NOTIFICATIONS_LOAN";
    private static final String NOTIFICACATION_APAY="NOTIFICACATION_APAY";
    private static final String IS_PUSH="IS_PUSH";
    private static final String IS_PIN_ON="IS_PIN_ON";

    private SharedPreferences sPref;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sPref = context.getApplicationContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        editor = sPref.edit();
    }
    public void clearCache(){
        editor.clear();
        editor.apply();
    }


    public Integer getCityId(){
        return sPref.getInt(CITYID,0);
    }
    public void setCityid(Integer cityid){
        editor.putInt(CITYID,cityid);
        editor.apply();
    }
    public String getAva(){
        return sPref.getString(AVA,"");
    }
    public void setAva(String ava){
        editor.putString(AVA,ava);
        editor.apply();
    }
    public String getPassportback(){
        return sPref.getString(PASSPORTBACK,"");
    }
    public void setPassportback(String passportback){
        editor.putString(PASSPORTBACK,passportback);
        editor.apply();
    }
    public Boolean getUseBiometry(){
        return sPref.getBoolean(USE_BIOMETRY,false);
    }
    public void setUseBiometry(Boolean useBiometry){
        editor.putBoolean(USE_BIOMETRY,useBiometry);
        editor.apply();
    }
    public String getPassportfront(){
        return sPref.getString(PASSPORTFRONT,"");
    }
    public void setPassportfront(String passportfront){
        editor.putString(PASSPORTFRONT,passportfront);
        editor.apply();
    }
    public String getBirthday(){
        return sPref.getString(BIRTHDAY,"");
    }
    public void setBirthday(String birthday){
        editor.putString(BIRTHDAY,birthday);
        editor.apply();
    }
    public String getLastname(){
        return sPref.getString(LASTNAME,"");
    }
    public void setLastname(String lastname){
        editor.putString(LASTNAME,lastname);
        editor.apply();
    }
    public String getSecondname(){
        return sPref.getString(SECONDNAME,"");
    }
    public void setSecondname(String secondname){
        editor.putString(SECONDNAME,secondname);
        editor.apply();
    }
    public void setFirstname(String firstname){
        editor.putString(FIRSTNAME,firstname);
        editor.apply();
    }
    public String getFirstname(){
        return sPref.getString(FIRSTNAME,"");
    }
    public void setInn(String inn){
        editor.putString(INN,inn);
        editor.apply();
    }
    public String getInn(){
        return sPref.getString(INN,"");
    }
    public void setRefreshtoken(String refreshtoken){
        editor.putString(REFRESHTOKEN,refreshtoken);
        editor.apply();
    }
    public String getRefreshToken(){
        return sPref.getString(REFRESHTOKEN,"");
    }
    public void setToken(String token){
        editor.putString(TOKEN,token);
        editor.apply();
    }
    public String getToken(){
        return sPref.getString(TOKEN,"");
    }
    public Boolean getIsLogged(){
        return sPref.getBoolean(IS_LOGGED,false);
    }
    public void setIsLogged(Boolean isLogged){
        editor.putBoolean(IS_LOGGED,isLogged);
        editor.apply();
    }
    public void setPin(String pin){
        editor.putString(PIN,pin);
        editor.apply();
    }
    public boolean checkPin(String pin){
        return sPref.getString(PIN,"").equals(pin);
    }
    public Long getBalance(){
        return sPref.getLong(BALANCE, (long) 0);
    }
    public void setBalance(Double balance){
        editor.putLong(BALANCE,balance.longValue());
        editor.apply();
    }
    public void setPhone(String phone){
        editor.putString(PHONE,phone);
        editor.apply();
    }
    public String getPhone(){
        return sPref.getString(PHONE,"");
    }

    public void setNotificacationApay(Integer count){
        editor.putInt(NOTIFICACATION_APAY,count);
        editor.apply();
    }
    public Integer getNotificationApay(){
        return sPref.getInt(NOTIFICACATION_APAY,0);
    }
    public void setNotificationsLoan(Integer count){
        editor.putInt(NOTIFICATIONS_LOAN,count);
        editor.apply();
    }
    public Integer getNotificationLoan(){
        return sPref.getInt(NOTIFICATIONS_LOAN,0);
    }
    public void setIsPush(Boolean isPush){
        editor.putBoolean(IS_PUSH,isPush);
        editor.apply();
    }
    public Boolean getIsPush(){
        return sPref.getBoolean(IS_PUSH,false);
    }
    public void setIsPinOn(Boolean isPinOn){
        editor.putBoolean(IS_PIN_ON,isPinOn);
        editor.apply();
    }
    public Boolean getisPinOn(){
        return sPref.getBoolean(IS_PIN_ON,true);
    }

}

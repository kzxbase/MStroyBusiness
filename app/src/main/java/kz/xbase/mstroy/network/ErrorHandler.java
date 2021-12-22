package kz.xbase.mstroy.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;


import kz.xbase.mstroy.activity.LoginActivity;
import kz.xbase.mstroy.utils.AlertDialogManager;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class ErrorHandler<T> {

    public static final int OTHER_AUTH = 401;
    public static final int UPDATE_APP = 406;
    public static final int SERVER_ERROR = 500;
    private Context context;
    private Response<T> response;

    public ErrorHandler(Context context, Response<T> response) {
        this.context = context;
        this.response = response;
        checkErrorResponse();
    }

    private void checkErrorResponse() {
        if (response.code() == OTHER_AUTH) {
            Intent intentReg = new Intent(context, LoginActivity.class);
            Toast.makeText(context, "Сессия устарела!", Toast.LENGTH_SHORT).show();
            context.startActivity(intentReg);
            ((Activity)context).finish();
        } else if (response.code() == UPDATE_APP) {
            new AlertDialogManager(context, "Вышло обновление нашего приложения. Пожалуйста обновите!", "Обновить приложение", true);
        } else if(response.code() == SERVER_ERROR){
            new AlertDialogManager(context,"Упс","Что-то пошло не так", false);
        }
        else {
            ErrorBody errorBody = convertError(response.errorBody());
            if (errorBody != null) {
                new AlertDialogManager(context,"Упс", errorBody.getMessage(), false);
            }
        }
    }

    private ErrorBody convertError(ResponseBody response) {
        if(response == null){
            return null;
        }
        try {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(response.string(), ErrorBody.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

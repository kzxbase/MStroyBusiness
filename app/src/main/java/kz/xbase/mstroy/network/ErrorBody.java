package kz.xbase.mstroy.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorBody {

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("code")
    @Expose
    public int code;

    @SerializedName("status")
    @Expose
    public int status;

    @SerializedName("type")
    @Expose
    public String type;

    public String getMessage() {
        return message;
    }
}

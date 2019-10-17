
package com.example.aymen.androidchat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageNet {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("uniqueId")
    @Expose
    private String uniqueId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}

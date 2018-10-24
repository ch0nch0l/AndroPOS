package me.chonchol.andropos.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mehedi.chonchol on 24-Oct-18.
 */

public class Client {

    private Integer clientId;
    private String clientUrl;
    private String clientPort;
    private String clientDBName;
    private String clientPin;
    @SerializedName("active")
    private Boolean isActive;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientUrl() {
        return clientUrl;
    }

    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }

    public String getClientPort() {
        return clientPort;
    }

    public void setClientPort(String clientPort) {
        this.clientPort = clientPort;
    }

    public String getClientDBName() {
        return clientDBName;
    }

    public void setClientDBName(String clientDBName) {
        this.clientDBName = clientDBName;
    }

    public String getClientPin() {
        return clientPin;
    }

    public void setClientPin(String clientPin) {
        this.clientPin = clientPin;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}

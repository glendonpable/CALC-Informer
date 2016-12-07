package com.fmpdroid.calcinformer.model;

/**
 * Created by sagara213@gmail.com on 12/3/2016.
 */
public class Subscriber {

    private String access_token;
    private String subscriber_number;

    public Subscriber(String access_token, String subscriber_number) {
        this.access_token = access_token;
        this.subscriber_number = subscriber_number;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getSubscriber_number() {
        return subscriber_number;
    }

    public void setSubscriber_number(String subscriber_number) {
        this.subscriber_number = subscriber_number;
    }
}

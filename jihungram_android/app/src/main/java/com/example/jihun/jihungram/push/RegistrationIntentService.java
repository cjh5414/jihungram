package com.example.jihun.jihungram.push;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.jihun.jihungram.Proxy;
import com.example.jihun.jihungram.ProxyUP;
import com.example.jihun.jihungram.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Administrator on 2016-07-23.
 */
public class RegistrationIntentService extends IntentService {
    private static AsyncHttpClient client = new AsyncHttpClient();
    public RegistrationIntentService() {
        super("RegistrationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            ProxyUP.uploadUser(getApplicationContext(), intent.getStringExtra("UserId"), new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA).format(new Date()), token);

        }catch (IOException e) {
            e.printStackTrace();
        }


    }


}

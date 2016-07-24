package com.example.jihun.jihungram.push;

import android.content.Intent;

/**
 * Created by Administrator on 2016-07-23.
 */
public class InstanceIDListenerService extends com.google.android.gms.iid.InstanceIDListenerService{
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}

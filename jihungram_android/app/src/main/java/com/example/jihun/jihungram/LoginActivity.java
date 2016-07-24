package com.example.jihun.jihungram;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.params.Face;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jihun.jihungram.push.RegistrationIntentService;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Administrator on 2016-07-22.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String ADMIN_ID = "ADMIN";
    private static final String ADMIN_PW = "ADMINPW";
    CallbackManager callbackManager;
    LoginButton loginButton;
    Button adminLoginBtn;
    LinearLayout adminIDLinear, adminPWLinear;
    EditText adminIDEditText, adminPWEditText;

    private static AsyncHttpClient client = new AsyncHttpClient();

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private Button mRegistrationButton;
    private ProgressBar mRegistrationProgressBar;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView mInformationTextView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    /**
     * Instance ID를 이용하여 디바이스 토큰을 가져오는 RegistrationIntentService를 실행한다.
     */
    public void getInstanceIdToken(String userId) {
        Intent intent = new Intent(this, RegistrationIntentService.class);
        intent.putExtra("UserId", userId);
        startService(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        adminLoginBtn = (Button) findViewById(R.id.admin_login_button);
        adminIDLinear = (LinearLayout) findViewById(R.id.admin_id_linear);
        adminPWLinear = (LinearLayout) findViewById(R.id.admin_pw_linear);
        adminIDEditText = (EditText) findViewById(R.id.amdin_id_editText);
        adminPWEditText = (EditText) findViewById(R.id.amdin_pw_editText);

        adminLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adminIDLinear.getVisibility() == View.INVISIBLE) {
                    adminIDLinear.setVisibility(View.VISIBLE);
                    adminPWLinear.setVisibility(View.VISIBLE);
                }
                else {
                    if(adminIDEditText.getText().toString().equals("") || adminPWEditText.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "ID와 PW를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else if(!adminIDEditText.getText().toString().equals(ADMIN_ID)) {
                        Toast.makeText(getApplicationContext(), "ID가 틀립니다.", Toast.LENGTH_SHORT).show();
                    } else if(!adminPWEditText.getText().toString().equals(ADMIN_PW)) {
                        Toast.makeText(getApplicationContext(), "Password가 틀립니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                Arrays.asList("public_profile", "email"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult result) {
                        getInstanceIdToken(result.getAccessToken().getUserId());
                        Log.e("test", "Facebook login Success");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.e("test", "Facebook login Error: " + error);
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        Log.e("test", "Facebook login cancel");
                        finish();
                    }
                }

        );
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client2.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Login Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://com.example.jihun.jihungram/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client2, viewAction);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Login Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://com.example.jihun.jihungram/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client2, viewAction);
//        client2.disconnect();
//    }
}
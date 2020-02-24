package com.example.vitinew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.ResponseListener;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TrueSDK;
import com.truecaller.android.sdk.TrueSdkScope;

import org.json.JSONException;
import org.json.JSONObject;

import static android.view.View.GONE;


public class LoginRegister extends AppCompatActivity {
int id;
String name,phone,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        if(SaveSharedPreference.getUserName(LoginRegister.this).length() == 0)
        {
            // call Login Activity
        }
        else
        { startActivity(new Intent(LoginRegister.this,MainActivity.class));
            finish();
            // Stay at the current activity.
        }
    }
    private final ITrueCallback sdkCallback = new ITrueCallback() {

        @Override
        public void onSuccessProfileShared(@NonNull final TrueProfile trueProfile) {

            // This method is invoked when the truecaller app is installed on the device and the user gives his
            // consent to share his truecaller profile
            Log.d( "True caller", "Verified Successfully : " + trueProfile.firstName );
        name=trueProfile.firstName+" "+trueProfile.lastName;
        email=trueProfile.email;
        phone=trueProfile.phoneNumber;


            UserController userController=new UserController(LoginRegister.this);
            JSONObject request=generateRequest();
           userController.postWithJsonRequest(API.TRUECALLER,request,responselistener);
        }

        @Override
        public void onFailureProfileShared(@NonNull final TrueError trueError) {
            // This method is invoked when some error occurs or if an invalid request for verification is made


        }

        @Override
        public void onVerificationRequired() {

        }

    };
    public void register(View view) {
        startActivity(new Intent(LoginRegister.this,Register.class));
    }

    public void login(View view) {

        startActivity(new Intent(LoginRegister.this,Login_activity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TrueSDK.getInstance().onActivityResultObtained( this,resultCode, data);
    }

    public void truecaller(View view) {
        TrueSdkScope trueScope = new TrueSdkScope.Builder(this, sdkCallback)
                .consentMode(TrueSdkScope.CONSENT_MODE_FULLSCREEN )
                .consentTitleOption( TrueSdkScope.SDK_CONSENT_TITLE_VERIFY )
                .footerType( TrueSdkScope.FOOTER_TYPE_SKIP )
                .build();
        TrueSDK.init(trueScope);
        if(TrueSDK.getInstance().isUsable()){
            TrueSDK.getInstance().getUserProfile(LoginRegister.this);
            // To display the user's Truecaller profile in a full screen view
        }
        else{

        }
    }
    private JSONObject generateRequest(){

        JSONObject jsonData = new JSONObject();

        try {
            jsonData.put("name", name);
            jsonData.put("email", email);
            jsonData.put("phone", phone);
         //   jsonData.put("id", );

            //mainObj.put("",jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonData;
    }
    private final ResponseListener responselistener = new ResponseListener() {

        @Override
        public void onRequestStart() {

        }

        @Override
        public void onSuccess(String response) {
            try {

                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code=jsonObject.getString("code");
                switch(code){
                    case "SUCCESS":
                        JSONObject user=jsonObject.getJSONObject("user");
                        SaveSharedPreference.setUserId(LoginRegister.this,Integer.parseInt(String.valueOf(user.get("id"))));

                        SaveSharedPreference.setUserName(LoginRegister.this,name);
                        Toast.makeText(LoginRegister.this, "Logging Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginRegister.this,MainActivity.class));
                        finish();

                        break;
                    case "REFRAL CODE DOES NOT EXIST":
                        Toast.makeText(LoginRegister.this, "REFRAL CODE DOES NOT EXIST", Toast.LENGTH_SHORT).show();
                        break;
                    case "EMAIL ALREADY EXISTS":
                        Toast.makeText(LoginRegister.this, "EMAIL ALREADY EXISTS", Toast.LENGTH_SHORT).show();
                        break;
                    case "PASSWORDS DO NOT MATCH":
                        Toast.makeText(LoginRegister.this, "PASSWORDS DO NOT MATCH", Toast.LENGTH_SHORT).show();
                        break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";

        }
    };

}

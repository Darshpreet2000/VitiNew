package com.example.vitinew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.ResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentViaPaytm extends AppCompatActivity {
    UserController userController;
    String paymentdetail,amount;
    Button WithdrawNow;
    EditText PhoneNumber,AmountDetail;

    private  void Withdraw(String detail){
        JSONObject jsn=new JSONObject();

        int id=1;
        String  uid=String.valueOf(SaveSharedPreference.getUserId(PaymentViaPaytm.this));
       try {
           jsn.put("uid",uid);
           jsn.put("method_id",1);
           jsn.put("amount",amount);
           jsn.put("details",detail);
       }
       catch (Exception e){

       }


        //+"?id="+2+"&uid="+uid+"&terms=true"
        Log.v("JSON is",jsn.toString());
        userController.postWithJsonRequest(API.Withdraw,jsn, ResponseListener);
    }
    private final ResponseListener ResponseListener = new ResponseListener() {

        @Override
        public void onRequestStart() {

        }

        @Override
        public void onSuccess(String response) {
            try {
                Log.d("str",response);
                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code=jsonObject.getString("code");
                switch(code){
                    case "SUCCESS":

                        Toast.makeText(PaymentViaPaytm.this, " we will process payout in 12-16 hours ", Toast.LENGTH_SHORT).show();

                        break;
                    default:
                        Toast.makeText(PaymentViaPaytm.this, ""+code, Toast.LENGTH_SHORT).show();
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
            error.printStackTrace();
            Toast.makeText(PaymentViaPaytm.this, "USER BALANCE IS NOT SUFFICIENT", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_via_paytm);
        WithdrawNow=findViewById(R.id.withdrawMoney);
        PhoneNumber=findViewById(R.id.PaytmNumber);
        AmountDetail=findViewById(R.id.Amount);
        userController=new UserController(PaymentViaPaytm.this);

        WithdrawNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(AmountDetail.getText().toString())<500){
                    Toast.makeText(PaymentViaPaytm.this, "Amount Should Grater then 500 Rs", Toast.LENGTH_SHORT).show();
                }
                else {
                    paymentdetail=PhoneNumber.getText().toString();
                    amount=AmountDetail.getText().toString();
                    Withdraw(paymentdetail);
                }
            }
        });







    }
}

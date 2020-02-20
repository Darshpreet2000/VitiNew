package com.example.vitinew;

import androidx.appcompat.app.AppCompatActivity;

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

public class PaymentViaAmazonGiftCard extends AppCompatActivity {

    UserController userController;
    String paymentdetail, amount;
    Button WithdrawNow;
    EditText Emaildetail, AmountDetail;

    private void Withdraw(String detail) {
        JSONObject jsn = new JSONObject();

        int id = 1;
        String uid = String.valueOf(SaveSharedPreference.getUserId(PaymentViaAmazonGiftCard.this));
        try {
            jsn.put("uid", uid);
            jsn.put("method_id", 2);
            jsn.put("amount", amount);
            jsn.put("details", detail);
        } catch (Exception e) {

        }


        //+"?id="+2+"&uid="+uid+"&terms=true"
        Log.v("JSON is", jsn.toString());
        userController.postWithJsonRequest(API.Withdraw, jsn, ResponseListener);
    }

    private final com.example.vitinew.Webrequest.ResponseListener ResponseListener = new ResponseListener() {

        @Override
        public void onRequestStart() {

        }

        @Override
        public void onSuccess(String response) {
            try {
                Log.d("str", response);
                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code = jsonObject.getString("code");
                switch (code) {
                    case "SUCCESS":

                        Toast.makeText(PaymentViaAmazonGiftCard.this, " we will process payout in 12-16 hours ", Toast.LENGTH_SHORT).show();

                        break;
                    default:
                        Toast.makeText(PaymentViaAmazonGiftCard.this, "" + code, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(PaymentViaAmazonGiftCard.this, "USER BALANCE IS NOT SUFFICIENT", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_via_amazon_gift_card);
        WithdrawNow = findViewById(R.id.ButtonWithdrawAmazonCard);
        Emaildetail = findViewById(R.id.emailDetail);
        AmountDetail = findViewById(R.id.WithdrawAmazonAmount);
        userController = new UserController(PaymentViaAmazonGiftCard.this);

        WithdrawNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(AmountDetail.getText().toString()) < 500) {
                    Toast.makeText(PaymentViaAmazonGiftCard.this, "Amount Should Greater then 500 Rs", Toast.LENGTH_SHORT).show();
                } else {

                            amount= AmountDetail.getText().toString();
                    paymentdetail = Emaildetail.getText().toString();
                    Withdraw(paymentdetail);
                }
            }
        });


    }
}

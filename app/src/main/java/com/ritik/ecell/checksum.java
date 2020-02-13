package com.ritik.ecell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ritik.ecell.Events_Fragments.EventsMain;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class checksum extends AppCompatActivity implements PaytmPaymentTransactionCallback {
    String custid="", orderId="", mid="",amt="",faqid="",teamname="",teamleader="",teammember1="",teammember2="",teammember3="",str="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_main);
        //initOrderId();
        //PaytmPGService service=PaytmPGService.getProductionService();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Intent intent = getIntent();
        orderId = intent.getExtras().getString("orderid");
        custid = intent.getExtras().getString("custid");
        amt = intent.getExtras().getString("amount");
        faqid = intent.getExtras().getString("faqid");
        teamname = intent.getExtras().getString("teamname");
        teamleader = intent.getExtras().getString("teamleader");
        teammember1 = intent.getExtras().getString("teammember1");
        teammember2 = intent.getExtras().getString("teammember2");
        teammember3 = intent.getExtras().getString("teammember3");
        str = intent.getExtras().getString("str");

        mid = "KriIns08204252501061"; /// your marchant key
        sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
// vollye , retrofit, asynch
    }
    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String> {
        private ProgressDialog dialog = new ProgressDialog(checksum.this);
        //private String orderId , mid, custid, amt;
        String url ="https://retired-grid.000webhostapp.com/paytm/generateChecksum.php";
        String varifyurl = "https://securegw.paytm.in/theia/paytmCallback";
        String CHECKSUMHASH ="";
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }
        protected String doInBackground(ArrayList<String>... alldata) {
            JSONParser jsonParser = new JSONParser(checksum.this);
            String param=
                    "MID="+mid+
                            "&ORDER_ID=" + orderId+
                            "&CUST_ID="+custid+
                            "&CHANNEL_ID=WAP&TXN_AMOUNT="+amt+"&WEBSITE=DEFAULT"+
                            "&CALLBACK_URL="+ varifyurl+"&INDUSTRY_TYPE_ID=Retail";
            JSONObject jsonObject = jsonParser.makeHttpRequest(url,"POST",param);
            // yaha per checksum ke saht order id or status receive hoga..
            Log.e("CheckSum result >>",jsonObject.toString());
            if(jsonObject != null){
                Log.e("CheckSum result >>",jsonObject.toString());
                try {
                    CHECKSUMHASH=jsonObject.has("CHECKSUMHASH")?jsonObject.getString("CHECKSUMHASH"):"";
                    Log.e("CheckSum result >>",CHECKSUMHASH);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return CHECKSUMHASH;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.e(" setup acc ","  signup result  " + result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            PaytmPGService Service = PaytmPGService.getProductionService();
            // when app is ready to publish use production service
            // PaytmPGService  Service = PaytmPGService.getProductionService();
            // now call paytm service here
            //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values
            HashMap<String, String> paramMap = new HashMap<String, String>();
            //these are mandatory parameters
            paramMap.put("MID", mid); //MID provided by paytm
            paramMap.put("ORDER_ID", orderId);
            paramMap.put("CUST_ID", custid);
            paramMap.put("CHANNEL_ID", "WAP");
            paramMap.put("TXN_AMOUNT", amt);
            paramMap.put("WEBSITE", "DEFAULT");
            paramMap.put("CALLBACK_URL" ,varifyurl);
            //paramMap.put( "EMAIL" , "abc@gmail.com");   // no need
            // paramMap.put( "MOBILE_NO" , "9144040888");  // no need
            paramMap.put("CHECKSUMHASH" ,CHECKSUMHASH);
            //paramMap.put("PAYMENT_TYPE_ID" ,"CC");    // no need
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");
            PaytmOrder Order = new PaytmOrder(paramMap);
            Log.e("checksum ", "param "+ paramMap.toString());
            Service.initialize(Order,null);
            // start payment service call here
            Service.startPaymentTransaction(checksum.this, true, true,
                    checksum.this  );
        }
    }

    @Override
    public void onTransactionResponse(Bundle bundle) {
        Log.e("checksum ", " respon true " + bundle.toString());
        String status = bundle.getString("STATUS");
        if (status.equals("TXN_FAILURE"))
        {
            Toast.makeText(getApplicationContext(), "PAYMENT CANCELED" , Toast.LENGTH_LONG).show();
            Intent intent = new Intent(checksum.this, EventsMain.class);
            startActivity(intent);
            finish();
        }
        else if (status.equals("TXN_SUCCESS"))
        {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("registrations").child(faqid).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            databaseReference.keepSynced(true);
            databaseReference.child("userid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
            databaseReference.child("faqid").setValue(faqid);
            databaseReference.child("amount").setValue(amt);
            databaseReference.child("orderid").setValue(orderId);
            if (str.equals("0")){
                databaseReference.child("teamname").setValue(teamname);
                databaseReference.child("teamleader").setValue(teamleader);
            }
            else if (str.equals("1")) {

                DatabaseReference data1 = FirebaseDatabase.getInstance().getReference().child("Users");
                data1.keepSynced(true);
                data1.orderByChild("refrelid").equalTo(teammember1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null){
                            Support_Team support_team = dataSnapshot.getValue(Support_Team.class);
                            String uid = support_team.getUid();

                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("registrations").child(faqid).child(uid);
                            databaseReference1.keepSynced(true);
                            databaseReference1.child("userid").setValue(uid);
                            databaseReference1.child("faqid").setValue(faqid);
                            databaseReference1.child("amount").setValue(amt);
                            databaseReference1.child("orderid").setValue(orderId);
                            databaseReference1.child("teamname").setValue(teamname);
                            databaseReference1.child("teamleader").setValue(teamleader);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                databaseReference.child("teamname").setValue(teamname);
                databaseReference.child("teamleader").setValue(teamleader);
                databaseReference.child("teammember1").setValue(teammember1);
            }
            else if (str.equals("2")) {

                DatabaseReference data1 = FirebaseDatabase.getInstance().getReference().child("Users");
                data1.keepSynced(true);
                data1.orderByChild("refrelid").equalTo(teammember1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null){
                            Support_Team support_team = dataSnapshot.getValue(Support_Team.class);
                            String uid = support_team.getUid();

                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("registrations").child(faqid).child(uid);
                            databaseReference1.keepSynced(true);
                            databaseReference1.child("userid").setValue(uid);
                            databaseReference1.child("faqid").setValue(faqid);
                            databaseReference1.child("amount").setValue(amt);
                            databaseReference1.child("orderid").setValue(orderId);
                            databaseReference1.child("teamname").setValue(teamname);
                            databaseReference1.child("teamleader").setValue(teamleader);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DatabaseReference data2 = FirebaseDatabase.getInstance().getReference().child("Users");
                data2.keepSynced(true);
                data2.orderByChild("refrelid").equalTo(teammember2).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null){
                            Support_Team support_team = dataSnapshot.getValue(Support_Team.class);
                            String uid = support_team.getUid();

                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("registrations").child(faqid).child(uid);
                            databaseReference1.keepSynced(true);
                            databaseReference1.child("userid").setValue(uid);
                            databaseReference1.child("faqid").setValue(faqid);
                            databaseReference1.child("amount").setValue(amt);
                            databaseReference1.child("orderid").setValue(orderId);
                            databaseReference1.child("teamname").setValue(teamname);
                            databaseReference1.child("teamleader").setValue(teamleader);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                databaseReference.child("teamname").setValue(teamname);
                databaseReference.child("teamleader").setValue(teamleader);
                databaseReference.child("teammember1").setValue(teammember1);
                databaseReference.child("teammember2").setValue(teammember2);
            }
            else if (str.equals("3")) {

                DatabaseReference data1 = FirebaseDatabase.getInstance().getReference().child("Users");
                data1.keepSynced(true);
                data1.orderByChild("refrelid").equalTo(teammember1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null){
                            Support_Team support_team = dataSnapshot.getValue(Support_Team.class);
                            String uid = support_team.getUid();

                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("registrations").child(faqid).child(uid);
                            databaseReference1.keepSynced(true);
                            databaseReference1.child("userid").setValue(uid);
                            databaseReference1.child("faqid").setValue(faqid);
                            databaseReference1.child("amount").setValue(amt);
                            databaseReference1.child("orderid").setValue(orderId);
                            databaseReference1.child("teamname").setValue(teamname);
                            databaseReference1.child("teamleader").setValue(teamleader);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DatabaseReference data2 = FirebaseDatabase.getInstance().getReference().child("Users");
                data2.keepSynced(true);
                data2.orderByChild("refrelid").equalTo(teammember2).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null){
                            Support_Team support_team = dataSnapshot.getValue(Support_Team.class);
                            String uid = support_team.getUid();

                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("registrations").child(faqid).child(uid);
                            databaseReference1.keepSynced(true);
                            databaseReference1.child("userid").setValue(uid);
                            databaseReference1.child("faqid").setValue(faqid);
                            databaseReference1.child("amount").setValue(amt);
                            databaseReference1.child("orderid").setValue(orderId);
                            databaseReference1.child("teamname").setValue(teamname);
                            databaseReference1.child("teamleader").setValue(teamleader);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                DatabaseReference data3 = FirebaseDatabase.getInstance().getReference().child("Users");
                data3.keepSynced(true);
                data3.orderByChild("refrelid").equalTo(teammember3).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null){
                            Support_Team support_team = dataSnapshot.getValue(Support_Team.class);
                            String uid = support_team.getUid();

                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("registrations").child(faqid).child(uid);
                            databaseReference1.keepSynced(true);
                            databaseReference1.child("userid").setValue(uid);
                            databaseReference1.child("faqid").setValue(faqid);
                            databaseReference1.child("amount").setValue(amt);
                            databaseReference1.child("orderid").setValue(orderId);
                            databaseReference1.child("teamname").setValue(teamname);
                            databaseReference1.child("teamleader").setValue(teamleader);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                databaseReference.child("teamname").setValue(teamname);
                databaseReference.child("teamleader").setValue(teamleader);
                databaseReference.child("teammember1").setValue(teammember1);
                databaseReference.child("teammember2").setValue(teammember2);
                databaseReference.child("teammember3").setValue(teammember3);
            }

            //Toast.makeText(getApplicationContext(), "PAYMENT SUCCESS" , Toast.LENGTH_LONG).show();
            Intent intent = new Intent(checksum.this, EventsMain.class);
            intent.putExtra("amount",amt);
            startActivity(intent);
            finish();
        }
        else if (status.equals("PENDING"))
        {
            Toast.makeText(getApplicationContext(), "PAYMENT PENDING", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(checksum.this, EventsMain.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public void networkNotAvailable() {
        Toast.makeText(getApplicationContext(), "PAYMENT FAILED " , Toast.LENGTH_LONG).show();
        Intent intent = new Intent(checksum.this, EventsMain.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void clientAuthenticationFailed(String s) {
        Intent intent = new Intent(checksum.this, EventsMain.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void someUIErrorOccurred(String s) {
        Log.e("checksum ", " ui fail respon  "+ s );
        Intent intent = new Intent(checksum.this, EventsMain.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Intent intent = new Intent(checksum.this, EventsMain.class);
        startActivity(intent);
        finish();
        Log.e("checksum ", " error loading pagerespon true "+ s + "  s1 " + s1);
    }
    @Override
    public void onBackPressedCancelTransaction() {
        Intent intent = new Intent(checksum.this, EventsMain.class);
        startActivity(intent);
        finish();
        Log.e("checksum ", " cancel call back respon  " );
        Toast.makeText(getApplicationContext(), "PAYMENT CANCELED" , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Intent intent = new Intent(checksum.this, EventsMain.class);
        startActivity(intent);
        finish();
        Log.e("checksum ", "  transaction cancel " );
        Toast.makeText(getApplicationContext(), "PAYMENT CANCELED" , Toast.LENGTH_LONG).show();
    }
}
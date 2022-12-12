package com.example.live_action_outer_side;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button Access_btn, Sbmt_btn;
    Spinner Education,Ocupation ;
    EditText et_abt;
CheckBox tw_wlr,refrgtr,pc,Ac,colrtv,wshingmchine,car,agri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dialog dialog = new Dialog(MainActivity.this);

//        List<String> categories = new ArrayList<String>();
//        categories.add("Automobile");
//        categories.add("electronics");
//        categories.add("Business Services");
//        categories.add("Computers");
//        categories.add("Education");
//        categories.add("Personal");
//        categories.add("Travel");

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Education.setAdapter(adapter);
//////////////////////////////////////////////
        dialog.setContentView(R.layout.data);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;


        Access_btn = dialog.findViewById(R.id.accessicibilty);
        Sbmt_btn= dialog.findViewById(R.id.submit);
        Education =dialog.findViewById(R.id.Edu_spn);
        Ocupation = dialog.findViewById(R.id.ocup_spn);
        tw_wlr=dialog.findViewById(R.id.ckbx_twler);
        refrgtr=dialog.findViewById(R.id.ckbx_refrigtr);
        wshingmchine=dialog.findViewById(R.id.ckbx_wshingmchin);
        car=dialog.findViewById(R.id.ckbx_car);
        colrtv=dialog.findViewById(R.id.ckbx_clrtv);
        agri=dialog.findViewById(R.id.ckbx_agri);
        pc=dialog.findViewById(R.id.ckbx_pc);
        Ac=dialog.findViewById(R.id.ckbx_ac);
        et_abt = dialog.findViewById(R.id.et_about);
        ///////
        ArrayAdapter<CharSequence>edu_adap=ArrayAdapter.createFromResource(this, R.array.Education, android.R.layout.simple_spinner_item);

        edu_adap.setDropDownViewResource(android.R.layout.simple_spinner_item);
        Education.setAdapter(edu_adap);
        ArrayAdapter<CharSequence>occu_adap=ArrayAdapter.createFromResource(this, R.array.Ocupation, android.R.layout.simple_spinner_item);

        occu_adap.setDropDownViewResource(android.R.layout.simple_spinner_item);
        Ocupation.setAdapter(occu_adap);


        //////

      //  String res = result.toString();


        Access_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog.dismiss();
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
//                Toast.makeText(MainActivity.this, "okay clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Sbmt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder result=new StringBuilder();

                if(agri.isChecked()){
                    result.append("Agricultural,");
                }
                if(car.isChecked()){
                    result.append("Car / Jeep/ Van,");
                }
                if(tw_wlr.isChecked()){
                    result.append("two Wheeler,");
                }
                if(Ac.isChecked()){
                    result.append("Air Conditioner,");
                }
                if(wshingmchine.isChecked()){
                    result.append("Washing machine,");
                }
                if(colrtv.isChecked()){
                    result.append("Color tv,");
                }
                if(refrgtr.isChecked()){
                    result.append("Refrigerator,");
                }
                if(pc.isChecked()){
                    result.append("Personal Computer / Laptop ,");
                }
               // Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);
                String education= (String)Education.getSelectedItem();
                String occupation= (String)Ocupation.getSelectedItem();
                String chekbox_choices=result.toString();
                String About = et_abt.getText().toString().trim();
                String U_abt= About;
                String U_edu=  education;
                String U_ocu=  occupation;
                String U_Chek =  chekbox_choices;
 //               String U_data = About+education+occupation+chekbox_choices;
                String urlString = "https://4465-122-169-93-39.in.ngrok.io/user/create"; // URL to call
                // String data = params[1]; //data to post
               // OutputStream out = null;



                try {
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    urlConnection.setRequestProperty("Accept","application/json");
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);

                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("about", U_abt);
                    jsonBody.put("education", U_edu);
                    jsonBody.put("occupation", U_ocu);
                    jsonBody.put("durables_used", U_Chek);



                    Log.i("jsonBody", jsonBody.toString());
                    DataOutputStream o = new DataOutputStream(urlConnection.getOutputStream());

                    o.writeBytes(jsonBody.toString());
                    o.flush();
                    o.close();

                    Log.e("connn",String.valueOf(urlConnection.getResponseCode()));
                    Log.e("connn",String.valueOf(urlConnection.getResponseMessage()));


                    urlConnection.disconnect();
                } catch (Exception e) {
                    Log.e("techh", String.valueOf(e));
                    System.out.println(e.getMessage());
                }



//
//                String[] PERMISSIONS = {
//
//                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
//                        android.Manifest.permission.ACCESS_FINE_LOCATION,
//                        android.Manifest.permission.CAMERA
//                };
//                ActivityCompat.requestPermissions(MainActivity.this,PERMISSIONS,1);
//                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{ACCESS_COARSE_LOCATION},2);
//                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{ACCESS_FINE_LOCATION},3);

              //  dialog.dismiss();
                Toast.makeText(MainActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();


    }

}
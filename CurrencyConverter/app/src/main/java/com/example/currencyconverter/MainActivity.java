package com.example.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.currencyconverter.Retrofit.RetroFitBuilder;
import com.example.currencyconverter.Retrofit.RetroFitInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btn;
    EditText currencyToBeConverted, currencyConverted;
    Spinner convertToDropdown, convertFromDropdown;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    String item1, item2;
    Member member;


    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyConverted = (EditText) findViewById(R.id.editTextResult);
        currencyToBeConverted = (EditText) findViewById(R.id.editText);
        convertToDropdown = (Spinner)findViewById(R.id.toCurrency);
        database=FirebaseDatabase.getInstance();
        databaseReference= database.getReference("Country");
        convertFromDropdown =(Spinner) findViewById(R.id.fromCurrency);
        convertFromDropdown.setOnItemSelectedListener(this);
        convertToDropdown.setOnItemSelectedListener(this);
        btn = (Button) findViewById(R.id.button);
        member=new  Member();

        Animation anim= AnimationUtils.loadAnimation(this,R.anim.animation);
        convertToDropdown.setAnimation(anim);
        Animation anim2= AnimationUtils.loadAnimation(this,R.anim.animation);
        convertFromDropdown.setAnimation(anim2);
        Animation anim3= AnimationUtils.loadAnimation(this,R.anim.animation);
        currencyToBeConverted.setAnimation(anim3);
        Animation anim4= AnimationUtils.loadAnimation(this,R.anim.animation);
        currencyConverted.setAnimation(anim4);


        String[] dropDownList={"USD","AED","AFN","ALL","AMD","ANG","AOA","ARS",
                "AUD","AWG","AZN","BAM","BBD","BDT","BGN","BHD",
                "BIF","BMD","BND","BOB","BRL","BSD","BTN","BWP",
                "BYN","BZD","CAD","CDF","CHF","CLP","CNY","COP",
                "CRC","CUC","CUP","CVE","CZK","DJF","DKK","DOP",
                "DZD","EGP","ERN","ETB","EUR","FJD","FKP","FOK",
                "GBP","GEL","GGP","GHS","GIP","GMD","GNF","GTQ",
                "GYD","HKD","HNL","HRK","HTG","HUF","IDR","ILS",
                "IMP","INR","IQD","IRR","ISK","JMD","JOD",
                "JPY","KES","KGS","KHR","KID","KMF","KRW",
                "KWD","KYD","KZT","LAK","LBP","LKR","LRD",
                "LSL","LYD","MAD","MDL","MGA","MKD","MMK",
                "MNT","MOP","MRU","MUR","MVR","MWK","MXN",
                "MYR","MZN","NAD","NGN","NIO","NOK","NPR","NZD",
                "OMR","PAB","PEN","PGK","PHP","PKR","PLN","PYG",
                "QAR","RON","RSD","RUB","RWF","SAR","SBD","SCR",
                "SDG","SEK","SGD","SHP","SLL","SOS","SRD","SSP",
                "STN","SYP","SZL","THB","TJS","TMT","TND","TOP",
                "TRY","TTD","TVD","TWD","TZS","UAH","UGX","UYU",
                "UZS","VES","VND","VUV","WST","XAF","XCD",
                "XDR","XOF","XPF","YER","ZAR","ZMW"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, dropDownList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        convertToDropdown.setAdapter(adapter);
        convertFromDropdown.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void Convert(View view) {
        RetroFitInterface retroFitInterface= RetroFitBuilder.getRetrofitInstance().create(RetroFitInterface.class);
        Call<JsonObject> call=retroFitInterface.getExchangeCurrency(convertFromDropdown.getSelectedItem()
                .toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                JsonObject res= response.body();

                JsonObject rates=res.getAsJsonObject("conversion_rates");

                double currency =  Double.valueOf(currencyToBeConverted.getText().toString());
                double multiplier=Double.valueOf(rates.get(convertToDropdown.getSelectedItem().toString()).toString());
                double result=currency*multiplier;
                currencyConverted.setText(String.valueOf(result));




            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
        if (item1 == "" && item2==""){
            Toast.makeText(this,"Please Select a planet", Toast.LENGTH_LONG).show();
        }
        else {
            member=new Member(item1, item2);
            databaseReference.child("Currency").setValue(member);
            Toast.makeText(this, "Successful", Toast.LENGTH_LONG).show();
        }

}

//    public void Save(View view) {
//        if (item1 == "" && item2==""){
//            Toast.makeText(this,"Please Select a planet", Toast.LENGTH_LONG).show();
//        }
//        else {
//            member=new Member(item1, item2);
//            databaseReference.child("Currency").setValue(member);
//            Toast.makeText(this, "Successful", Toast.LENGTH_LONG).show();
//        }
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item1=convertFromDropdown.getSelectedItem().toString();
        item2=convertToDropdown.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void Favourites(MenuItem item) {
        Intent intent=new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }
}
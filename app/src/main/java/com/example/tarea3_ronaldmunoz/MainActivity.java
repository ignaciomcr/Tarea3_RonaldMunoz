package com.example.tarea3_ronaldmunoz;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarea3_ronaldmunoz.Fragment.ConvertFragment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {

    public String contenidoURL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Buscar();

        //getSupportFragmentManager().beginTransaction().add(android.R.id.content,new ConvertFragment()).commit();

        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        ConvertFragment fragment1=new ConvertFragment();
        Bundle bundle=new Bundle();

        //TextView tv=(TextView)findViewById(R.id.tvContenido);
        //bundle.putSerializable("conteJason",tv.getText().toString());
        bundle.putSerializable("conteJason",contenidoURL);
        fragment1.setArguments(bundle);
        ft.replace(android.R.id.content,fragment1);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void Buscar() {


        boolean validar;
        validar = hasInternetAccess();

        if (validar == false) {
            Toast.makeText(getApplicationContext(), "NO HAY CONEXIÓN....", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Buscando....", Toast.LENGTH_SHORT).show();

            try {

                contenidoURL= getJsonFromUrl("http://www.apilayer.net/api/live?access_key=bb6c7d5318f8e4e96e6f100a9ba089d7&currencies=CUP,JPY,HNL,VEF,AUD");

            } catch (IOException e) {
                                e.printStackTrace();
            }


        }
    }

        public boolean hasInternetAccess(){
            ConnectivityManager cm =(ConnectivityManager)getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
            //Para poder habilitar el getActiveNetworkInfo HAY QUE IR AL MANIFEST para dar permisos.
            //<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
            //<uses-permission android:name="android.permission.INTERNET"></uses-permission>
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            return  netInfo != null && netInfo.isConnectedOrConnecting();
        }



    public String getJsonFromUrl(String urlString) throws IOException{
        StringBuilder result = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert urlConnection != null;
            urlConnection.disconnect();
        }
        return result.toString();
    }


}

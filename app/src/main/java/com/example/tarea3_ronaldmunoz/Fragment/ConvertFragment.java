package com.example.tarea3_ronaldmunoz.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarea3_ronaldmunoz.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.support.v4.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConvertFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConvertFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConvertFragment extends Fragment {


    public Double valorAu = 0.0;
    public Double valorCu = 0.0;
    public Double valorHn  = 0.0;
    public Double valorJp = 0.0;
    public Double valorVe  = 0.0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_convert, container, false);
        CargarValores(view);
        return view;

    }

    public void CargarValores(final View view){

        final EditText etCantidad = (EditText)view.findViewById(R.id.txtCantidad);
        etCantidad.setText("1");
        etCantidad.setSelection(etCantidad.getText().length());

        //TextView tvvalor=(TextView)view.findViewById(R.id.tvFragment);

        Bundle bundle=getArguments();
        String datos=(String)bundle.getSerializable("conteJason");

        //tvvalor.setText(datos);

        final TextView tvAu=(TextView)view.findViewById(R.id.txtAu);
        final TextView tvCu=(TextView)view.findViewById(R.id.txtCu);
        final TextView tvHn=(TextView)view.findViewById(R.id.txtHn);
        final TextView tvJp=(TextView)view.findViewById(R.id.txtJp);
        final TextView tvVe=(TextView)view.findViewById(R.id.txtVe);

        JSONObject response = null;
        try {
            //response = new JSONObject(tvvalor.getText().toString());
            response = new JSONObject(datos);
            String success = response.getString("success");
            JSONObject currencies = response.getJSONObject("quotes");
            valorAu = currencies.getDouble("USDAUD");
            valorCu = currencies.getDouble("USDCUP");
            valorHn = currencies.getDouble("USDHNL");
            valorJp = currencies.getDouble("USDJPY");
            valorVe = currencies.getDouble("USDVEF");

            tvAu.setText(valorAu.toString());
            tvCu.setText(valorCu.toString());
            tvHn.setText(valorHn.toString());
            tvJp.setText(valorJp.toString());
            tvVe.setText(valorVe.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }




        Button mbutton=(Button)view.findViewById(R.id.btConvert);

        mbutton.setOnClickListener(new View.OnClickListener() {

                                       @Override
                                       public void onClick(View v) {



                                           if (etCantidad.getText().toString().trim().equals("")){
                                               etCantidad.setText("1");
                                               etCantidad.setSelection(etCantidad.getText().length());
                                           }

                                           Double valorFinal = 0.0;

                                           valorFinal = valorAu * Double.parseDouble(etCantidad.getText().toString());
                                           tvAu.setText(valorFinal.toString());

                                           valorFinal = 0.0;

                                           valorFinal = valorCu * Double.parseDouble(etCantidad.getText().toString());
                                           tvCu.setText(valorFinal.toString());

                                           valorFinal = 0.0;

                                           valorFinal = valorHn * Double.parseDouble(etCantidad.getText().toString());
                                           tvHn.setText(valorFinal.toString());

                                           valorFinal = 0.0;

                                           valorFinal = valorJp * Double.parseDouble(etCantidad.getText().toString());
                                           tvJp.setText(valorFinal.toString());

                                           valorFinal = 0.0;

                                           valorFinal = valorVe * Double.parseDouble(etCantidad.getText().toString());
                                           tvVe.setText(valorFinal.toString());


                                       }
                                   }
        );



    }


}

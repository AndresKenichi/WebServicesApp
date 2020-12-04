package com.example.webservicesapp.ui;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.webservicesapp.R;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrarUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrarUsuarioFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {

    EditText campoDoc,campoNombre,campoProfesion;
    Button btnRegistrar;
    ProgressDialog progreso;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistrarUsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrarUsuarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrarUsuarioFragment newInstance(String param1, String param2) {
        RegistrarUsuarioFragment fragment = new RegistrarUsuarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registrar_usuario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        campoDoc=view.findViewById(R.id.campoDoc);
        campoNombre=view.findViewById(R.id.campoNombre);
        campoProfesion=view.findViewById(R.id.campoProfesion);
        btnRegistrar=view.findViewById(R.id.btnRegistrar);

        request= Volley.newRequestQueue(getContext());

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cargarWebService();

            }


        });

    }

    private void cargarWebService() {

        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();


        String url="http://192.168.43.213:8292/ejemploBDRemota/wsJSONRegistro.php?documento="+campoDoc.getText().toString()+"&nombre="+campoNombre.getText().toString()+"&profesion="+campoProfesion.getText().toString();

        url=url.replace(" ","%20");

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }


    @Override
    public void onResponse(JSONObject response) {

        progreso.hide();
        Toast.makeText(getContext(),"Se ha registrado exitosamente",Toast.LENGTH_SHORT).show();
        campoDoc.setText("");
        campoNombre.setText("");
        campoProfesion.setText("");

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(),"No se pudo registrar "+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR: ",error.toString());

    }


}
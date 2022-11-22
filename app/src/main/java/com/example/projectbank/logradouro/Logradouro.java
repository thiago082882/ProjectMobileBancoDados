package com.example.projectbank.logradouro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectbank.Mask;
import com.example.projectbank.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Logradouro extends AppCompatActivity {

    TextInputEditText edtId,  edEndereco,edCep,edComplemento;
    Button btnBuscar, btnInserir, btnEditar, btnExcluir;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logradouro);

        edtId = findViewById(R.id.edtId);
        edEndereco = findViewById(R.id. edEndereco);
        edCep = findViewById(R.id.edCep);
        edComplemento = findViewById(R.id.edComplemento);
        btnInserir = findViewById(R.id.btnInserir);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnEditar = findViewById(R.id.btnEditar);
        btnExcluir = findViewById(R.id.btnExcluir);

        btnBuscar.setOnClickListener(view -> buscarConta("http://10.0.0.101/fateczonasul/buscar_logradouro.php?id=" + edtId.getText()));
        btnInserir.setOnClickListener(view -> executarServico("http://10.0.0.101/fateczonasul/inserir_logradouro.php"));
        btnEditar.setOnClickListener(view -> executarServico("http://10.0.0.101/fateczonasul/editar_logradouro.php"));
        btnExcluir.setOnClickListener(view -> excluirConta("http://10.0.0.101/fateczonasul/excluir_logradouro.php"));
        maskZip();
    }


    private void executarServico(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERAÇÃO BEM-SUCEDIDA ", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id", edtId.getText().toString());
                parametros.put("endereco", edEndereco.getText().toString());
                parametros.put("cep", edCep.getText().toString());
                parametros.put("Complemento", edComplemento.getText().toString());

                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void buscarConta(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        edEndereco.setText(jsonObject.getString("endereco"));
                        edCep.setText(jsonObject.getString("cep"));
                        edComplemento.setText(jsonObject.getString("Complemento"));

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Erro de Conexão", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void excluirConta(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "CONTA EXCLUIDA ", Toast.LENGTH_SHORT).show();
                limparFormulario();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id", edtId.getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void limparFormulario() {
        edtId.setText("");
        edEndereco.setText("");
        edCep.setText("");
        edComplemento.setText("");

    }

    private void maskZip() {
        edCep.addTextChangedListener(
                Mask.insert(Mask.ZIP_MASK, edCep));
    }

}
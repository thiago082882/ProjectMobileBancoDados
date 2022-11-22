package com.example.projectbank.conta;

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

public class Conta extends AppCompatActivity {

    TextInputEditText edtId, edAgencia,edNumero,edDataAbertura,edStatus,edSaldo;
    Button btnBuscar, btnInserir, btnEditar, btnExcluir;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);

        edtId = findViewById(R.id.edtId);
        edAgencia = findViewById(R.id.edAgencia);
        edNumero = findViewById(R.id.edNumero);
        edDataAbertura = findViewById(R.id.edDataAbertura);
        edStatus = findViewById(R.id.edStatus);
        edSaldo = findViewById(R.id.edSaldo);
        btnInserir = findViewById(R.id.btnInserir);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnEditar = findViewById(R.id.btnEditar);
        btnExcluir = findViewById(R.id.btnExcluir);

        btnBuscar.setOnClickListener(view -> buscarConta("http://10.0.0.101/fateczonasul/buscar_conta.php?id=" + edtId.getText()));
        btnInserir.setOnClickListener(view -> executarServico("http://10.0.0.101/fateczonasul/inserir_conta.php"));
        btnEditar.setOnClickListener(view -> executarServico("http://10.0.0.101/fateczonasul/editar_conta.php"));
        btnExcluir.setOnClickListener(view -> excluirConta("http://10.0.0.101/fateczonasul/excluir_conta.php"));
        maskNumber();
        maskDate();
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
                parametros.put("agencia", edAgencia.getText().toString());
                parametros.put("numero", edNumero.getText().toString());
                parametros.put("data_abertura", edDataAbertura.getText().toString());
                parametros.put("status", edStatus.getText().toString());
                parametros.put("saldo", edSaldo.getText().toString());
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
                        edAgencia.setText(jsonObject.getString("agencia"));
                        edNumero.setText(jsonObject.getString("numero"));
                        edDataAbertura.setText(jsonObject.getString("data_abertura"));
                        edStatus.setText(jsonObject.getString("status"));
                        edSaldo.setText(jsonObject.getString("saldo"));
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
        edAgencia.setText("");
        edNumero.setText("");
        edDataAbertura.setText("");
        edStatus.setText("");
        edSaldo.setText("");
    }

    private void maskNumber() {
        edNumero.addTextChangedListener(
                Mask.insert(Mask.NUMBER_MASK, edNumero));
    }

    private void maskDate() {
        edDataAbertura.addTextChangedListener(
                Mask.insert(Mask.DATE_MASK, edDataAbertura));
    }

}
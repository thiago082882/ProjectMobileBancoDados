package com.example.projectbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.projectbank.cliente.Cliente;
import com.example.projectbank.conta.Conta;
import com.example.projectbank.banco.Banco;
import com.example.projectbank.logradouro.Logradouro;

public class Home extends AppCompatActivity {
CardView cdBanco,cdConta,cdCliente,cdLogradouro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cdBanco=findViewById(R.id.cdBanco);
        cdConta=findViewById(R.id.cdConta);
        cdCliente=findViewById(R.id.cdCliente);
        cdLogradouro=findViewById(R.id.cdLogradouro);

        cdBanco.setOnClickListener(view -> {
            SharedPreferences preferences=getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
            preferences.edit().clear().commit();
            Intent intent = new Intent(getApplicationContext(), Banco.class);
            startActivity(intent);
            finish();
        });


        cdConta.setOnClickListener(view -> {

            SharedPreferences preferences=getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
            preferences.edit().clear().commit();
            Intent intent = new Intent(getApplicationContext(), Conta.class);
            startActivity(intent);
            finish();
        });

        cdCliente.setOnClickListener(view -> {

            SharedPreferences preferences=getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
            preferences.edit().clear().commit();
            Intent intent = new Intent(getApplicationContext(), Cliente.class);
            startActivity(intent);
            finish();
        });

        cdLogradouro.setOnClickListener(view -> {

            SharedPreferences preferences=getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
            preferences.edit().clear().commit();
            Intent intent = new Intent(getApplicationContext(), Logradouro.class);
            startActivity(intent);
            finish();
        });



    }
}
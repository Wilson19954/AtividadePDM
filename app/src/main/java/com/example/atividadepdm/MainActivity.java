package com.example.atividadepdm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edTitulo = findViewById(R.id.edTitulo);
        EditText edAnoLancamento = findViewById(R.id.edAnoLancamento);
        Spinner spGenero = findViewById(R.id.edGenero);
        EditText edDuracao = findViewById(R.id.edDuracao);
        Button btCadastrar = findViewById(R.id.btCadastrar);
        Button btRemover = findViewById(R.id.btRemover);

        String urlWebservice = "http://10.0.2.2:5000/api/Filme";
        RequestQueue req = Volley.newRequestQueue(MainActivity.this);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(edTitulo.getText().length() > 0 && edAnoLancamento.getText().length() > 0 && edDuracao.getText().length() > 0)
        {
            JSONObject enviarDados = new JSONObject();
            try{
                enviarDados.put("id", 0); //Autoincremento do Banco de dados
                enviarDados.put("titulo", edTitulo.getText().toString());
                enviarDados.put("anoLancamento", Double.parseDouble(edAnoLancamento.getText().toString()));
                enviarDados.put("genero",spGenero.getSelectedItem().toString());
                enviarDados.put("duracao", Double.parseDouble(edDuracao.getText().toString()));
            } catch(JSONException jExc){
                jExc.printStackTrace();
            }
            JsonObjectRequest config = new JsonObjectRequest(
                    Request.Method.POST,
                    urlWebservice,
                    enviarDados,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if(response.has("titulo")){
                                Toast.makeText(MainActivity.this,
                                        "Filme Cadastrado!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this,
                            "Erro ao cadastrar filme", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }
            );
            req.add(config);
            limparCampos();
        } else {
                Toast.makeText(MainActivity.this, "Preencha os campos em branco", Toast.LENGTH_SHORT).show();
            }
   }
        });
        btRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TelaRemover.class));
            }
        });
   }
    private void limparCampos(){
        ConstraintLayout telaCadastro = findViewById(R.id.telaCadastro);
        for (int i = 0; i < telaCadastro.getChildCount(); i++) {
            View view = telaCadastro.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
        }
    }
}
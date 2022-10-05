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

public class TelaAtualizar extends AppCompatActivity {

    EditText edTitulo, edAnoLancamento, edDuracao;
    Spinner spGenero;

    int idFilme = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_atualizar);

        Intent valores = getIntent();
        idFilme = valores.getIntExtra("id", 0);
        String titulo = valores.getStringExtra("titulo");
        String anoLancamento = String.valueOf(valores.getDoubleExtra("anoLancamento", 0));
        String duracao = String.valueOf(valores.getDoubleExtra("duracao", 0));
        int position = valores.getIntExtra("genero", 0);
        //String genero = valores.getStringExtra("genero");

        edTitulo = findViewById(R.id.edTitulo);
        edAnoLancamento = findViewById(R.id.edAnoLancamento);
        spGenero = findViewById(R.id.edGenero);
        edDuracao = findViewById(R.id.edDuracao);
        Button btAtualizar = findViewById(R.id.btAtualizar);

        edTitulo.setText(titulo);
        edAnoLancamento.setText(anoLancamento);
        edDuracao.setText(duracao);
        spGenero.setSelection(position);

        btAtualizar.setOnClickListener(view -> {
            if(camposVazios()){
                Toast.makeText(TelaAtualizar.this, "Verifique se ficou algum campo vazio", Toast.LENGTH_SHORT).show();
            }else{
                    enviarDadosWebservice();
            }
        });
    }


    private void enviarDadosWebservice(){
        String url = "http://10.0.2.2:5000/api/Filme";

        try {
            JSONObject dadosEnvio = new JSONObject();
            dadosEnvio.put("id", idFilme);
            dadosEnvio.put("titulo", edTitulo.getText().toString());
            dadosEnvio.put("anoLancamento", edAnoLancamento.getText().toString());
            dadosEnvio.put("duracao", edDuracao.getText().toString());
            dadosEnvio.put("genero",spGenero.getSelectedItem().toString());

            JsonObjectRequest configRequisicao = new JsonObjectRequest(Request.Method.PUT,
                    url, dadosEnvio,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getInt("status") == 200){
                                    Toast.makeText(TelaAtualizar.this, "Filme Editado com sucesso!", Toast.LENGTH_SHORT).show();
                                    limparCampos();
                                    startActivity(new Intent(TelaAtualizar.this, TelaRemover.class));
                                }else{
                                    Toast.makeText(TelaAtualizar.this, R.string.avisoErro, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(TelaAtualizar.this, R.string.avisoErro, Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(TelaAtualizar.this, R.string.avisoErro, Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            RequestQueue requisicao = Volley.newRequestQueue(TelaAtualizar.this);
            requisicao.add(configRequisicao);

        }catch (Exception exc){
            exc.printStackTrace();
        }
    }

    private boolean camposVazios(){
        ConstraintLayout telaComponentes = findViewById(R.id.telaComponentes);
        for (int i = 0; i < telaComponentes.getChildCount(); i++) {
            View view = telaComponentes.getChildAt(i);
            if (view instanceof EditText) {
                if(((EditText) view).getText().toString().equals("")){
                    return true;
                }
            }
        }
        return false;
    }
    private void limparCampos(){
        ConstraintLayout telaComponentes = findViewById(R.id.telaComponentes);
        for (int i = 0; i < telaComponentes.getChildCount(); i++) {
            View view = telaComponentes.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }
        }
    }

}
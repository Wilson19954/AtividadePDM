package com.example.atividadepdm;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TelaRemover extends AppCompatActivity {
    List<Filmes> listaFilmes = new ArrayList<>();

    String padrao = "#.##";
    DecimalFormat df = new DecimalFormat(padrao);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_remover);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(TelaRemover.this));

        String url = "http://10.0.2.2:5000/api/Filme";
        RequestQueue requisicao = Volley.newRequestQueue(TelaRemover.this);

        JsonArrayRequest config = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0 ; i<response.length() ; i++){
                            try{
                                JSONObject filme = response.getJSONObject(i);
                                int id = filme.getInt("id");
                                String titulo = filme.getString("titulo");
                                double anoLancamento = filme.getDouble("anoLancamento");
                                String genero = filme.getString("genero");
                                double duracao = filme.getDouble("duracao");
                                duracao = duracao / 60;
                                double duracaof = Double.parseDouble(df.format(duracao));
                                Filmes f = new Filmes(id, titulo, anoLancamento, genero, duracaof);
                                listaFilmes.add(f);
                            }catch (JSONException jExc){
                                jExc.printStackTrace();
                            }
                        }
                        FilmesAdapter adapter = new FilmesAdapter(listaFilmes, TelaRemover.this);
                        recyclerView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //Imprime os erros no LogCat
                Toast.makeText(TelaRemover.this, R.string.avisoErro,
                        Toast.LENGTH_SHORT).show();
            }
        });
        requisicao.add(config);
    }
}
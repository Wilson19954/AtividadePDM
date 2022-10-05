package com.example.atividadepdm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

public class FilmesAdapter extends RecyclerView.Adapter<FilmesViewHolder> {

    private List<Filmes> listaFilmes;
    private Context context;
    AlertDialog alert;

    public FilmesAdapter(List<Filmes> listaFilmes, Context context){
        this.listaFilmes = listaFilmes;
        this.context = context;
    }

    @NonNull
    @Override
    public FilmesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new FilmesViewHolder(view);
    }

    String padrao = "#.##";
    DecimalFormat df = new DecimalFormat(padrao);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull FilmesViewHolder holder, int position) {

        FilmesViewHolder viewHolder  = (FilmesViewHolder) holder;

        viewHolder.txtTitulo.setText(listaFilmes.get(position).getTitulo());
        viewHolder.txtAnoLancamento.setText(Double.toString(listaFilmes.get(position).getAnoLancamento()));
        viewHolder.txtGenero.setText(listaFilmes.get(position).getGenero());
        viewHolder.txtDuracao.setText(Double.toString(listaFilmes.get(position).getDuracao()));

        viewHolder.imgBtRemover.setOnClickListener(view -> {
            AlertDialog.Builder confirmacao = new AlertDialog.Builder(holder.itemView.getContext());
            confirmacao.setTitle(R.string.txtTituloAlert);
            confirmacao.setPositiveButton(R.string.txtApagarSim, (dialog, which) -> {
                removerWebservice(listaFilmes.get(position).getId(), position);
            });
            confirmacao.setNegativeButton(R.string.txtApagarNao, (dialog, which) -> {
                alert.cancel();
            });
            alert = confirmacao.create();
            alert.show();
        });

        viewHolder.imgBtAtualizar.setOnClickListener(view -> {
            Intent it = new Intent(context, TelaAtualizar.class);
            it.putExtra("id", listaFilmes.get(position).getId());
            it.putExtra("titulo", listaFilmes.get(position).getTitulo());
            it.putExtra("anoLancamento", listaFilmes.get(position).getAnoLancamento());
            it.putExtra("genero", listaFilmes.get(position).getGenero());
            it.putExtra("duracao", listaFilmes.get(position).getDuracao());
            context.startActivity(it);
        });
    }

    private void removerWebservice(int id, int posicao){
        String url = "http://10.0.2.2:5000/api/Filme/" + id;

        JsonObjectRequest configRequisicao = new JsonObjectRequest(Request.Method.DELETE,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getInt("status") == 200){
                                listaFilmes.remove(posicao);
                                notifyDataSetChanged();
                                Toast.makeText(context, R.string.avisoOk, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, R.string.avisoErro, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, R.string.avisoErro, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(context, R.string.avisoErro, Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requisicao = Volley.newRequestQueue(context);
        requisicao.add(configRequisicao);
    }

    private void atualizar (int id, int posicao){
        String url = "http://10.0.2.2:5000/api/Filme/" + id;
        JsonObjectRequest configRequisicao = new JsonObjectRequest(Request.Method.DELETE,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getInt("status") == 200){
                                listaFilmes.remove(posicao);
                                notifyDataSetChanged();
                                Toast.makeText(context, R.string.avisoOk, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, R.string.avisoErro, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, R.string.avisoErro, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(context, R.string.avisoErro, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requisicao = Volley.newRequestQueue(context);
        requisicao.add(configRequisicao);
    }

    @Override
    public int getItemCount() {
        return listaFilmes.size();
    }
}

class FilmesViewHolder extends RecyclerView.ViewHolder{
    TextView txtTitulo, txtAnoLancamento, txtGenero, txtDuracao;
    ImageButton imgBtAtualizar, imgBtRemover;

    public FilmesViewHolder(@NonNull View itemView) {
        super(itemView);

        txtTitulo = itemView.findViewById(R.id.txtTitulo);
        txtAnoLancamento = itemView.findViewById(R.id.txtAnoLancamento);
        txtGenero = itemView.findViewById(R.id.txtGenero);
        txtDuracao = itemView.findViewById(R.id.txtDuracao);
        imgBtRemover = itemView.findViewById(R.id.imgBtRemoverItem);
        imgBtAtualizar = itemView.findViewById(R.id.imgBtEditarItem);
    }
}
package com.example.atividadepdm;

import java.text.DecimalFormat;

public class Filmes {

    public int id;
    public String titulo;
    public double anoLancamento;
    public String genero;
    public double duracao;

    String padrao = "#.##";
    DecimalFormat df = new DecimalFormat(padrao);

    public Filmes(int id, String titulo, double anoLancamento, String genero, double duracao) {
        this.id = id;
        this.titulo = titulo;
        this.anoLancamento = anoLancamento;
        this.genero = genero;
        this.duracao = duracao;
    }

    public Filmes() {    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getTitulo() {return titulo;}

    public void setTitulo(String titulo) {this.titulo = titulo;}

    public double getAnoLancamento() {return anoLancamento;}

    public void setAnoLancamento(double anoLancamento) {this.anoLancamento = anoLancamento;}

    public String getGenero() {return genero;}

    public void setGenero(String genero) {this.genero = genero;}

    public double getDuracao() { return duracao;}

    public void setDuracao(double duracao) {this.duracao = duracao;}

    public String toString(){
        return "ID do filme: " + id + "     Titulo do Filme: " + titulo + "     Ano de Lancamento: " + anoLancamento +
                "       Genêro: " + genero + "      Duração do filme em horas: " + df.format(duracao);
    }
}

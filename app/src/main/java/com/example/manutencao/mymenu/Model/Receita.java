package com.example.manutencao.mymenu.Model;

/**
 * Created by Rafael Delanhese on 22/05/2018.
 */

public class Receita {

    private Long idReceita;

    private String titulo;
    private Categoria categoria;
    private Origem origem;
    private String ingredientes;
    private String modoPreparo;
    private String porcao;
    private String valorEstimado;

    public Receita() {
    }

    public Receita(Long idReceita, String titulo, Categoria categoria, Origem origem, String ingredientes, String modoPreparo, String porcao, String valorEstimado) {
        this.idReceita = idReceita;
        this.titulo = titulo;
        this.categoria = categoria;
        this.origem = origem;
        this.ingredientes = ingredientes;
        this.modoPreparo = modoPreparo;
        this.porcao = porcao;
        this.valorEstimado = valorEstimado;
    }

    public Receita(String titulo, Categoria categoria, Origem origem) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.origem = origem;
    }

    public Long getIdReceita() {
        return idReceita;
    }

    public void setIdReceita(Long idReceita) {
        this.idReceita = idReceita;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Origem getOrigem() {
        return origem;
    }

    public void setOrigem(Origem origem) {
        this.origem = origem;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getModoPreparo() {
        return modoPreparo;
    }

    public void setModoPreparo(String modoPreparo) {
        this.modoPreparo = modoPreparo;
    }

    public String getPorcao() {
        return porcao;
    }

    public void setPorcao(String porcao) {
        this.porcao = porcao;
    }

    public String getValorEstimado() {
        return valorEstimado;
    }

    public void setValorEstimado(String valorEstimado) {
        this.valorEstimado = valorEstimado;
    }

    @Override
    public String toString(){
        return String.format("Título: %s\nCategoria: %s\nOrigem: %s", titulo, categoria.getDescricao(), origem.getDescricao());
    }
}

package com.example.manutencao.mymenu.Model;

/**
 * Created by Rafael Delanhese on 22/05/2018.
 */

public class Categoria {

    private Long idCategoria;

    private String descricao;

    public Categoria() {
    }


    public Categoria(Long idCategoria, String descricao) {
        this.idCategoria = idCategoria;
        this.descricao = descricao;
    }

    public Categoria(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

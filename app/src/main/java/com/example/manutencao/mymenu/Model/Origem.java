package com.example.manutencao.mymenu.Model;

/**
 * Created by Rafael Delanhese on 22/05/2018.
 */

public class Origem {

    private Long idOrigem;

    private String descricao;

    public Origem() {
    }

    public Origem(String descricao) {
        this.descricao = descricao;
    }

    public Origem(Long idOrigem, String descricao) {
        this.idOrigem = idOrigem;
        this.descricao = descricao;
    }

    public Long getIdOrigem() {
        return idOrigem;
    }

    public void setIdOrigem(Long idOrigem) {
        this.idOrigem = idOrigem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

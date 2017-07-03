package com.example.leona.hope4all;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * Created by portol on 29/05/2017.
 */

public class Doacao {

    private String categoria, descricao, emailUsuario, emailInstituicao, status, horarioEntrega, enderecoEntrega;
    private TipoDoacao tipo;
    private String data;

    public Doacao(String categoria, String descricao, String emailUsuario, String emailInstituicao, TipoDoacao tipo, String status) {
        this.categoria = categoria;
        this.descricao = descricao;
        this.emailUsuario = emailUsuario;
        this.emailInstituicao = emailInstituicao;
        this.tipo = tipo;
        this.status = status;
        data = DateTime.now().withZone(DateTimeZone.forID("America/Sao_Paulo")).toString("dd-MM-YYYY HH:mm");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHorarioEntrega() {
        return horarioEntrega;
    }

    public void setHorarioEntrega(String horarioEntrega) {
        this.horarioEntrega = horarioEntrega;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Doacao(){}

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getEmailInstituicao() {
        return emailInstituicao;
    }

    public void setEmailInstituicao(String emailInstituicao) {
        this.emailInstituicao = emailInstituicao;
    }

    public TipoDoacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoDoacao tipo) {
        this.tipo = tipo;
    }
}

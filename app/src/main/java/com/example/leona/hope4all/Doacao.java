package com.example.leona.hope4all;

import android.icu.text.TimeZoneFormat;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.TimeZone;

/**
 * Created by portol on 29/05/2017.
 */

public class Doacao {

    private String categoria, descricao, emailUsuario, emailInstituicao;
    private TipoDoacao tipo;
    private String data;

    public Doacao(String categoria, String descricao, String emailUsuario, String emailInstituicao, TipoDoacao tipo) {
        this.categoria = categoria;
        this.descricao = descricao;
        this.emailUsuario = emailUsuario;
        this.emailInstituicao = emailInstituicao;
        this.tipo = tipo;
        data = DateTime.now().withZone(DateTimeZone.forID("America/Sao_Paulo")).toString("dd-MM-YYYY HH:mm");
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

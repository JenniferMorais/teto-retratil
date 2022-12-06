package com.example.tetoretratil;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Usuario {

    String id;
    String nome;
    Integer status;
    Integer limpeza;

    public Usuario() {}

    public Usuario(String id, String nome, Integer status, Integer limpeza) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.limpeza = limpeza;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLimpeza() {
        return limpeza;
    }

    public void setLimpeza(Integer limpeza) {
        this.limpeza = limpeza;
    }

    public void salvar(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("usuarios").child(getId()).setValue(this);
    }
}

package com.example.registrousuarios_movil.utils;


import java.io.Serializable;

public class Nodo<T> implements Serializable {

    public T Info;
    private Nodo<T> Sig;

    public Nodo(T D) {
        Info = D;
        Sig = null;
    }

    public Nodo<T> getSig() {
        return Sig;
    }

    public void setSig(Nodo<T> Ap) {
        Sig = Ap;
    }
}

package com.example.registrousuarios_movil.utils;


public class Nodo<T> {

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

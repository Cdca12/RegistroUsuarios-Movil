package com.example.registrousuarios_movil.models;

import com.example.registrousuarios_movil.utils.Rutinas;
import com.example.registrousuarios_movil.utils.UtilsCriterioOrdenamiento;

import java.io.Serializable;

/**
 *
 * @author Carlos Contreras
 */
public class Usuario implements Serializable {

    private String nombre;
    private int edad;
    private double estatura;
    private int criterioOrdenamiento;

    public Usuario(String nombre, int edad, double estatura, int criterioOrdenamiento) {
        this.nombre = nombre;
        this.edad = edad;
        this.estatura = estatura;
        this.criterioOrdenamiento = criterioOrdenamiento;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public double getEstatura() {
        return estatura;
    }
    

    // Cambiar el criterio de ordenamiento desde el Main
    public void setCriterioOrdenamiento(int criterioOrdenamiento) {
        this.criterioOrdenamiento = criterioOrdenamiento;
    }

    @Override
    public String toString() {
        switch (criterioOrdenamiento) {
            case UtilsCriterioOrdenamiento.POR_NOMBRE:
                return Rutinas.PonBlancos(nombre, 50);
            case UtilsCriterioOrdenamiento.POR_EDAD:
                return Rutinas.PonCeros(edad, 5);
            case UtilsCriterioOrdenamiento.POR_ESTATURA:
                return Rutinas.PonCeros(estatura, 5);
            case UtilsCriterioOrdenamiento.POR_EDAD_ESTATURA_NOMBRE:
                return Rutinas.PonCeros(edad, 5) + Rutinas.PonCeros(estatura, 5) + Rutinas.PonBlancos(nombre, 50);
            default:
                return "";
        }
    }

    public String mostrarInformacion() {
        return "Nombre: " + nombre + "\t| Edad: " + edad + "\t| Estatura: " + estatura;
    }

}

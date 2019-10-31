/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;

/**
 *
 * @author Milton
 */
public class Plato {

    protected int id;
    protected String nombre;
    protected TipoPlato tipoPlato;
    protected double precio;
    protected boolean esPrioridad;
    protected int tiempoPreparacion;
    protected boolean estaListo;
    protected ArrayList<Integer> calificaciones; 
    
    private static int ID_AUTO = 1;

    public Plato(String nombre, TipoPlato tipoPlato, double precio, int tiempoPreparacion) {
        this.id = ID_AUTO++;
        this.nombre = nombre;
        this.tipoPlato = tipoPlato;
        this.precio = precio;
        this.esPrioridad = false;
        this.tiempoPreparacion = tiempoPreparacion;
        this.estaListo = false;
        this.calificaciones = new ArrayList<>();
    }
    
    public void cambiarEstado() {
        estaListo = true;
    }
    
    public void calificar(int calificacion) {
        calificaciones.add(calificacion);
    }

    @Override
    public String toString() {
        return "Plato{" + "id=" + id + ", nombre=" + nombre + ", precio=" + precio + '}';
    }
}

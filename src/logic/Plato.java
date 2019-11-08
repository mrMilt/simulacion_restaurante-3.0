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
    protected boolean estaEntregado;
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
        this.estaEntregado = false;
        this.calificaciones = new ArrayList<>();
    }
    
    public void cambiarEstado() {
//        System.out.println("{{{{{{{{{{{{{ ");
        estaListo = true;
    }
    
    public void entregarPlato() {
        System.out.println("-----el plato se entrego");
        estaEntregado = true;
    }
    
    public synchronized void calificar(int calificacion) {
        calificaciones.add(calificacion);
    }

    public double obtenerPromedioCalificaciones() {
        int total = 0;
        for (int i = 0; i < calificaciones.size(); i++) {
            Integer c = calificaciones.get(i);
            total += c;
        }
        return total/calificaciones.size();
    }
    
    @Override
    public String toString() {
        return "Plato{" + "id=" + id + ", nombre=" + nombre + ", calf=" + calificaciones.size() + " entregado " + estaEntregado + " listo " + estaListo;
    }
}

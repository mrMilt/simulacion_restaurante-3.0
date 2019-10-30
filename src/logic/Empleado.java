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
public class Empleado {
    
    protected int id;
    protected int jornadaTrabajo;
    protected int jornadaDescanso;
    protected int intervaloDescanso;

    public Empleado(int id, int jornadaTrabajo, int jornadaDescanso, int intervaloDescanso) {
        this.id = id;
        this.jornadaTrabajo = jornadaTrabajo;
        this.jornadaDescanso = jornadaDescanso;
        this.intervaloDescanso = intervaloDescanso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJornadaTrabajo() {
        return jornadaTrabajo;
    }

    public void setJornadaTrabajo(int jornadaTrabajo) {
        this.jornadaTrabajo = jornadaTrabajo;
    }

    public int getJornadaDescanso() {
        return jornadaDescanso;
    }

    public void setJornadaDescanso(int jornadaDescanso) {
        this.jornadaDescanso = jornadaDescanso;
    }

    public int getIntervaloDescanso() {
        return intervaloDescanso;
    }

    public void setIntervaloDescanso(int intervaloDescanso) {
        this.intervaloDescanso = intervaloDescanso;
    }        
}

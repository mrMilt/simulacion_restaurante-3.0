/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author Milton
 */
public class Congelador {

    protected boolean estaDisponible;

    public Congelador() {
        this.estaDisponible = true;
    }
    
    public void ocupar() {
        estaDisponible = false;
    }

    public void desocupar() {
        estaDisponible = true;
    }
}

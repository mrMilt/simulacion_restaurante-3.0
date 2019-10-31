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
public class Pedido {
    
    protected Mesa mesa;
    protected ArrayList<Plato> platos; 

    public Pedido(Mesa mesa, ArrayList<Plato> platos) {
        this.mesa = mesa;
        this.platos = platos;
    }    

    @Override
    public String toString() {
        return "Pedido{" + "mesa=" + mesa.id + ", platos=" + platos + '}';
    }    
}

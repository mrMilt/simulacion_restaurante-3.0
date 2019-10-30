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
public class Menu {
    
   private ArrayList<Plato> platos;

    public Menu() {
        this.platos = new ArrayList<>();
    }
   
    public void agregarPlato(String nombre, TipoPlato tipoPlato, double precio, int tiempoPreparacion) {
        platos.add(new Plato(nombre, tipoPlato, precio, tiempoPreparacion));
    }
    
    public ArrayList<Plato> obtenerPlatosPorTipo(TipoPlato tipoPlato) {
        ArrayList<Plato> platosTipo = new ArrayList<>();
        for (int i = 0; i < platos.size(); i++) {
            Plato plato = platos.get(i);
            if (plato.tipoPlato == tipoPlato) {
                platosTipo.add(plato);
            }
        }
        return platosTipo;
    }
}

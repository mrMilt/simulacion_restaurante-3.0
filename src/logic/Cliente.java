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
public class Cliente {
    
    private int id;
    
    private static int ID_AUTO = 1;

    public Cliente() {
        this.id = ID_AUTO++;
    }
   
     public Mesa buscarMesaDisponible(ArrayList<Mesa> mesas) {
        for (int i = 0; i < mesas.size(); i++) {
            Mesa mesa = mesas.get(i);
            if (mesa.estaDisponible) {
                return mesa;
            }
        }
        return null;
    } 
     
    public ArrayList<Plato> seleccionarPedido(Menu menu) {
        ArrayList<Plato> platos = new ArrayList<>();
        ArrayList<Plato> entradas = menu.obtenerPlatosPorTipo(TipoPlato.ENTRADA);
        ArrayList<Plato> platosFuertes = menu.obtenerPlatosPorTipo(TipoPlato.PLATO_FUERTE);
        ArrayList<Plato> postres = menu.obtenerPlatosPorTipo(TipoPlato.POSTRE);        
        platos.add(platosFuertes.get((int) (Math.random() * platosFuertes.size())));
        
        boolean pedirEntrada = (int) (Math.random() * 2) == 1;
        boolean pedirUnPostre = (int) (Math.random() * 2) == 1;
        boolean pedirDosPostres = (int) (Math.random() * 2) == 1;
        
        if (pedirEntrada) {
            platos.add(entradas.get((int) (Math.random() * entradas.size())));
        } 
        if (pedirDosPostres) {
            platos.add(postres.get((int) (Math.random() * postres.size())));
            platos.add(postres.get((int) (Math.random() * postres.size())));
        } else if (pedirUnPostre) {
            platos.add(postres.get((int) (Math.random() * postres.size())));
        }
        
        return platos;
    }
    
    public void esperarPlato(Plato plato) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {                    
                    if (plato.estaListo) {
                        System.out.println("consumir plato");
                    }
                }
            }
        }).start();
    }
}

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
public final class Cocina {

    private ArrayList<Cocinero> cocineros;
    protected Congelador conjelador;

    private ArrayList<Pedido> pedidos;

    public Cocina() {
        this.cocineros = new ArrayList<>();
        this.conjelador = new Congelador();
        this.pedidos = new ArrayList<>();        
    }

    public void agregarPlatos(ArrayList<Pedido> pedidos) {
        this.pedidos.addAll(pedidos);
    }
    
    public void agregarCocinero(Cocinero cocinero) {
        this.cocineros.add(cocinero);
    }

    public void cocinar() {
        System.out.println("+++++++++++++++++++cocinar " + cocineros.size());
        for (int i = 0; i < cocineros.size(); i++) {
            Cocinero cocinero = cocineros.get(i);
            cocinero.prepararPlato(pedidos, conjelador);
        }
    }
    
    public void esperarPedidos(ArrayList<Mesero> meseros) {
        System.out.println("cocina: esperando pedidos");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (int i = 0; i < meseros.size(); i++) {
                        Mesero mesero = meseros.get(i);
                        System.out.println("cocina: cantidad pedidos " + mesero.pedidos.size());
                        if (mesero.pedidos.size() > 0 && !mesero.tomandoPedido) {
                            agregarPlatos(mesero.pedidos);
//                            mesero.pedidos.clear();
                        }
                    }                    
                    System.out.println("cocina: pedidos " + pedidos);
                }
            }
        }).start();
    }

    public ArrayList<Plato> obtenerPlatosPrioridad() {
        ArrayList<Plato> platosPrioridad = new ArrayList<>();
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pedido = pedidos.get(i);
            ArrayList<Plato> platos = pedido.platos;
            int cantidadPlatos = platos.size();
            for (int j = 0; j < cantidadPlatos; j++) {
                Plato plato = platos.get(j);
                if (plato.esPrioridad) {
                    platosPrioridad.add(plato);
                    platos.remove(plato);
                }
            }
        }
        return platosPrioridad;
    }
}

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
public class Cocina {

    private ArrayList<Cocinero> cocineros;
    protected Conjelador conjelador;

    private ArrayList<Pedido> pedidos;

    public Cocina() {
        this.cocineros = new ArrayList<>();
        this.conjelador = new Conjelador();
        this.pedidos = new ArrayList<>();
    }

    public void agregarPlatos(ArrayList<Pedido> pedidos) {
        pedidos.addAll(pedidos);
    }

    public void esperarPedidos(ArrayList<Mesero> meseros) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (int i = 0; i < meseros.size(); i++) {
                        Mesero mesero = meseros.get(i);
                        if (mesero.pedidos.size() > 0 && !mesero.tomandoPedido) {
                            agregarPlatos(mesero.pedidos);
                            mesero.pedidos.clear();
                        }
                    }
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

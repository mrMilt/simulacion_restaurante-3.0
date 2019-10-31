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
public class Restaurante {

    private ArrayList<Mesa> mesas;
    private ArrayList<Mesero> meseros;
    private Menu menu;
    private Cocina cocina;
    private CajaPagos cajaPagos;

    public Restaurante() {
        this.mesas = new ArrayList<>();
        this.meseros = new ArrayList<>();
        this.menu = new Menu();
        this.cocina = new Cocina();
        this.cajaPagos = new CajaPagos();
    }

    public void agregarMesas(int numeroMesas) {
        for (int i = 0; i < numeroMesas; i++) {
            mesas.add(new Mesa(5));
            System.out.println("");
        }
    }

    public void agregarMesero(int jornadaTrabajo, int jornadaDescanso, int intervaloDescanso) {
        meseros.add(new Mesero(jornadaTrabajo, jornadaDescanso, intervaloDescanso));
    }

    
    public void simular() {
        ArrayList<Cliente> colaClientes = new ArrayList<>();
        while (!colaClientes.isEmpty()) {
            Cliente cliente = colaClientes.remove(0);
            Mesa mesaDisponible = cliente.buscarMesaDisponible(mesas);
            if (mesaDisponible != null) {
                mesaDisponible.agregarClientes(cliente);
                mesaDisponible.cambiarANoDisponible();
                for (int i = 0; i < ((int) 1 + (Math.random() * 4)) - 1; i++) {
                    mesaDisponible.agregarClientes(cliente);
                }
            }
        }
    }
}

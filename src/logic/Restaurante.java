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
    private ArrayList<Cliente> colaClientes;
    private Menu menu;
    private Cocina cocina;
    private CajaPagos cajaPagos;

    public Restaurante() {
        this.mesas = new ArrayList<>();
        this.meseros = new ArrayList<>();
        this.colaClientes = new ArrayList<>();
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
    
    public void atenderMesas() {
        for (int i = 0; i < meseros.size(); i++) {
            Mesero mesero = meseros.get(i);
            mesero.atenderMesa(mesas, menu);
        }
    }
    
    public void atenderEnCaja() {
        cajaPagos.atenderClientes();
    }
    
    public void entregarPedidos() {
        for (int i = 0; i < meseros.size(); i++) {
            Mesero mesero = meseros.get(i);
            mesero.entregarPedidos();
        }
    }
    
    public void agregarCocinero(int jornadaTrabajo, int jornadaDescanso, int intervaloDescanso) {
        cocina.agregarCocinero(new Cocinero(jornadaTrabajo, jornadaDescanso, intervaloDescanso));
    }
    
    public void cocinar() {
        cocina.cocinar();
    }
    
    public void agregarCajero(int jornadaTrabajo, int jornadaDescanso, int intervaloDescanso) {
        cajaPagos.asignarCajero(jornadaTrabajo, jornadaDescanso, intervaloDescanso);
    }
    
    public void agregarPlatoAlMenu(String nombre, TipoPlato tipoPlato, double precio, int tiempoPreparacion) {
        menu.agregarPlato(nombre, tipoPlato, precio, tiempoPreparacion);
    }

    public void esperarPedidos() {
        cocina.esperarPedidos(meseros);
    }
    
    public void simular() {
        colaClientes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            colaClientes.add(new Cliente());
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("iniciando simulacion");
                while (!colaClientes.isEmpty()) {
                    System.out.println("clientes esperando " + colaClientes.size());
                    Cliente cliente = colaClientes.remove(0);
                    Mesa mesaDisponible = cliente.buscarMesaDisponible(mesas);
                    if (mesaDisponible != null) {
                        System.out.println("mesa disponible " + mesaDisponible.id);
                        cliente.cambiarEstaEnMesa();
                        cliente.seleccionarPedido(menu);
                        mesaDisponible.agregarClientes(cliente);
                        mesaDisponible.cambiarDisponibilidad();
                        for (int i = 0; i < ((int) (Math.random() * 4 + 1)) - 1 && !colaClientes.isEmpty(); i++) {
                            cliente = colaClientes.remove(0);
                            cajaPagos.agregarClientes(cliente);
                            cliente.seleccionarPedido(menu);
//                            cliente.esperarPlato(mesaDisponible, plato, mesero);
                            mesaDisponible.agregarClientes(cliente);
                        }
                        System.out.println("mesa esperando con " + mesaDisponible.clientesSentados.size() + " clientes");
                    }
                }
            }
        }).start();

    }
}

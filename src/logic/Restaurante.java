/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public void desocuparMesas() {
        for (int i = 0; i < mesas.size(); i++) {
            Mesa mesa = mesas.get(i);
            mesa.desocupar();
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
        for (int i = 0; i < 100; i++) {
            colaClientes.add(new Cliente());
        }
        int cantidadClientes = colaClientes.size();
//        System.out.println("cola clientes " + colaClientes);
        new Thread(new Runnable() {
            @Override
            public void run() {
//                System.out.println("iniciando simulacion");
                while (true) {
                    while (!colaClientes.isEmpty()) {
                        System.out.println("clientes esperando " + colaClientes.size());
                        Cliente cliente = colaClientes.get(0);
                        System.out.println("cliente sentado " + cliente.getId());
                        Mesa mesaDisponible = cliente.buscarMesaDisponible(mesas);
                        System.out.println("............... " + mesas);
                        if (mesaDisponible != null) {
//                            System.out.println("pppppppppppppppppp");
                            cajaPagos.agregarClientes(cliente);
//                            System.out.println("...................................... " + cajaPagos.colaCajero.toString());
                            colaClientes.remove(cliente);

//                        System.out.println("mesa disponible " + mesaDisponible.id);
                            cliente.cambiarEstaEnMesa();
//                        cliente.seleccionarPedido(menu);
                            mesaDisponible.agregarClientes(cliente);
//                            mesaDisponible.cambiarDisponibilidad();
                            System.out.println("mesa disp " + mesaDisponible.id + " disp " + mesaDisponible.estaDisponible + "   atendida " + mesaDisponible.estaAtendida + "  " + mesaDisponible.clientesSentados);
                            mesaDisponible.estaDisponible = false;
                            for (int i = 0; i < ((int) (Math.random() * 3 + 1)) && !colaClientes.isEmpty(); i++) {
                                cliente = colaClientes.remove(0);
                                System.out.println("cliente sentado " + cliente.getId());
                                cajaPagos.agregarClientes(cliente);
//                                System.out.println("...................................... " + cajaPagos.colaCajero.toString());
//                            cliente.seleccionarPedido(menu);
//                            cliente.esperarPlato(mesaDisponible, plato, mesero);
                                mesaDisponible.agregarClientes(cliente);

                            }
//                        System.out.println("cliente en cola: " + colaClientes.size());
//                        System.out.println("mesa esperando con " + mesaDisponible.clientesSentados.size() + " clientes");
                        }
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Restaurante.class.getName()).log(Level.SEVERE, null, ex);
                    }
//                    System.out.println("{ñññññññññññññññññññññññññññññññññññññññññññññññññññ " + Cajero.atendidos);
                    if (Cajero.atendidos >= cantidadClientes) {
                        System.out.println("platos: " + menu.platos);
                        System.exit(0);
                    }
                }
            }
        }).start();
    }
    
    public Plato obtenerTipoPlatoMasVendido(TipoPlato tipoPlato) {
        ArrayList<Plato> platos = menu.obtenerPlatosPorTipo(tipoPlato);
        Plato platoAux = platos.get(0);
        double max = platoAux.precio;
        for (int i = 1; i < platos.size(); i++) {
            Plato plato = platos.get(i);
            if (plato.precio > max) {
                max = plato.precio;
                platoAux = plato;
            }
        }
        return platoAux;
    }
    
    public Plato obtenerTipoPlatoMejorCalificado(TipoPlato tipoPlato) {
        ArrayList<Plato> platos = menu.obtenerPlatosPorTipo(tipoPlato);
        Plato platoAux = platos.get(0);
        double max = platoAux.obtenerPromedioCalificaciones();        
        for (int i = 1; i < platos.size(); i++) {
            Plato plato = platos.get(i);
            double promedioCalificaciones = plato.obtenerPromedioCalificaciones();
            if (promedioCalificaciones > max) {
                max = promedioCalificaciones;
                platoAux = plato;
            }
        }
        return platoAux;
    }
}

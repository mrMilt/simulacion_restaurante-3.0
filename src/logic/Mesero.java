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
public class Mesero extends Empleado {

    protected ArrayList<Pedido> pedidos;
    protected boolean tomandoPedido;
    protected ArrayList<Integer> calificaciones;
    protected ArrayList<Double> propinas;
    protected static ArrayList<Mesa> mesasPendientes;

    private final int MAX_PEDIDOS = 3;

    private static int ID_AUTO = 1;

    public Mesero(int jornadaTrabajo, int jornadaDescanso, int intervaloDescanso) {
        super(ID_AUTO++, jornadaTrabajo, jornadaDescanso, intervaloDescanso);
        this.pedidos = new ArrayList<>();
        this.tomandoPedido = true;
        this.calificaciones = new ArrayList<>();
        Mesero.mesasPendientes = new ArrayList<>();
        this.propinas = new ArrayList<>();
    }

    public synchronized void calificar(int calificacion) {
        calificaciones.add(calificacion);
    }
    
    public synchronized void recibirPropina(double propina) {
        propinas.add(propina);
    }

    public Mesa buscarMesaDisponible(ArrayList<Mesa> mesas) {
        for (int i = 0; i < mesas.size(); i++) {
            Mesa mesa = mesas.get(i);
            if (!mesa.estaDisponible && !mesa.estaAtendida) {
                return mesa;
            }
        }
        return null;
    }

    public synchronized void atenderMesa(ArrayList<Mesa> mesas, Menu menu) {
//        System.out.println("mesero " + id + " atendiendo mesas");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100 + (id * 10));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Mesero.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tomandoPedido = true;
                    for (int i = 0; i < MAX_PEDIDOS; i++) {
                        Mesa mesa = buscarMesaDisponible(mesas);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Mesero.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                        System.out.println("meser " + id + " mesas atendidadas " + Mesero.mesasPendientes);

                        if (mesa != null && !Mesero.mesasPendientes.contains(mesa)) {
                            Mesero.mesasPendientes.add(mesa);
//                            System.out.println("meser0: mesa d " + mesa.id);
//                            mesa.cambiarAtendida();
                            mesa.estaAtendida = true;
//                            mesas.remove(mesa);
//                            System.out.println("mesero " + id + " mesa " + mesa.estaAtendida);
//                            System.out.println("mesero*: " + id + " mesa: " + mesa.id);
                            ArrayList<Cliente> clientes = mesa.clientesSentados;
                            ArrayList<Plato> platos = new ArrayList<>();
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Mesero.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            for (int j = 0; j < clientes.size(); j++) {
                                Cliente cliente = clientes.get(j);
                                System.out.println("cliente pidiendo: " + cliente.getId());
                                ArrayList<Plato> platosAux = cliente.seleccionarPedido(menu);
                                cliente.esperarPlato(mesa, platosAux, Mesero.this);
                                platos.addAll(platosAux);
                            }

                            try {
                                //                            System.out.println("pedido " + platos);
                                Thread.sleep(5);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Mesero.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            pedidos.add(new Pedido(mesa, platos));
                        }
                    }
                    tomandoPedido = false;
                }
            }
        }).start();
    }

    public synchronized void entregarPedidos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
//                    System.out.println("***** " + pedidos);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Mesero.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    for (int i = 0; i < pedidos.size(); i++) {
                        Pedido pedido = pedidos.get(i);
                        ArrayList<Plato> platos = pedido.platos;
//                        System.out.println("................... " + platos.size());
                        while (!platos.isEmpty()) {
                            for (int j = 0; j < platos.size();) {
                                Plato plato = platos.get(j);
                                System.out.println("oooooooooooo");
                                System.out.println("*********mesero: " + plato.nombre + "   " + plato.estaListo);
                                if (plato.estaListo) {
//                                System.out.println("oe");
                                    try {
//                                    System.out.println("------------mesero:" + id +  " entregando plato");
                                        Thread.sleep(200);
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(Mesero.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    plato.entregarPlato();
                                    platos.remove(plato);
                                    System.out.println("oeeeeee " + platos);
//                                    if (platos.isEmpty()) {
//                                        System.out.println("oeeeeeeeeee");
////                                    Mesero.mesasPendientes.remove(pedido.mesa);
////                                    System.out.println("Mesero.mesasPendientes " + Mesero.mesasPendientes);
//                                        pedidos.remove(pedido);
//                                    }
                                } else {
                                    j++;
                                }
                            }
                        }
                         pedidos.remove(pedido);
                    }
                }
            }
        }).start();
    }
}

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

    private final int MAX_PEDIDOS = 3;

    private static int ID_AUTO = 1;

    public Mesero(int jornadaTrabajo, int jornadaDescanso, int intervaloDescanso) {
        super(ID_AUTO++, jornadaTrabajo, jornadaDescanso, intervaloDescanso);
        this.pedidos = new ArrayList<>();
        this.tomandoPedido = true;
        this.calificaciones = new ArrayList<>();
    }

    public void calificar(int calificacion) {
        calificaciones.add(calificacion);
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

    public void atenderMesa(ArrayList<Mesa> mesas, Menu menu) {
        System.out.println("mesero " + id + " atendiendo mesas");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Mesero.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tomandoPedido = true;
                    for (int i = 0; i < MAX_PEDIDOS; i++) {
                        Mesa mesa = buscarMesaDisponible(mesas);                        
                        if (mesa != null) {
                            System.out.println("mesero: " + id + " mesa: "+ mesa.id);
                            ArrayList<Cliente> clientes = mesa.clientesSentados;
                            ArrayList<Plato> platos = new ArrayList<>();
                            for (int j = 0; j < clientes.size(); j++) {
                                Cliente cliente = clientes.get(j);
                                platos.addAll(cliente.seleccionarPedido(menu));
                            }
                            mesa.cambiarAtendida();
                            System.out.println("pedido " + platos);
                            pedidos.add(new Pedido(mesa, platos));
                        }
                    }
                    tomandoPedido = false;
                }
            }
        }).start();
    }

    public void entregarPedidos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {                    
                    for (int i = 0; i < pedidos.size(); i++) {
                        Pedido pedido = pedidos.get(i);
                        ArrayList<Plato> platos = pedido.platos;
                        for (int j = 0; j < platos.size(); j++) {
                            Plato plato = platos.get(j);
                            if (plato.estaListo) {
                                platos.remove(plato);
                                if (platos.isEmpty()) {
                                    pedidos.remove(pedido);
                                }
                            }
                        }
                    }
                }
            }
        }).start();
    }
}

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
public class Mesero extends Empleado {

    protected ArrayList<Pedido> pedidos;
    protected boolean tomandoPedido;

    private final int MAX_PEDIDOS = 3;

    private static int ID_AUTO = 1;

    public Mesero(int jornadaTrabajo, int jornadaDescanso, int intervaloDescanso) {
        super(ID_AUTO++, jornadaTrabajo, jornadaDescanso, intervaloDescanso);
        this.tomandoPedido = true;
    }

    public Mesa buscarMesaDisponible(ArrayList<Mesa> mesas) {
        for (int i = 0; i < mesas.size(); i++) {
            Mesa mesa = mesas.get(i);
            if (!mesa.estaAtendida) {
                return mesa;
            }
        }
        return null;
    }

    public void atenderMesa(ArrayList<Mesa> mesas, Menu menu) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    tomandoPedido = true;
                    for (int i = 0; i < MAX_PEDIDOS; i++) {
                        Mesa mesa = buscarMesaDisponible(mesas);
                        if (mesa != null) {
                            mesa.cambiarAtendida();
                            ArrayList<Cliente> clientes = mesa.clientesSentados;
                            ArrayList<Plato> platos = new ArrayList<>();
                            for (int j = 0; j < clientes.size(); j++) {
                                Cliente cliente = clientes.get(j);
                                platos.addAll(cliente.seleccionarPedido(menu));
                            }
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

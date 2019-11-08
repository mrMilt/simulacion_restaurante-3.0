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
public class Cliente {

    private int id;
    protected boolean estaEnMesa;
    protected TipoPago tipoPago;

    private static int ID_AUTO = 1;

    public Cliente() {
        this.id = ID_AUTO++;
        this.estaEnMesa = false;
        this.tipoPago = TipoPago.values()[(int) (Math.random() * TipoPago.values().length)];
    }

    public int getId() {
        return id;
    }

    public Mesa buscarMesaDisponible(ArrayList<Mesa> mesas) {
        for (int i = 0; i < mesas.size(); i++) {
            Mesa mesa = mesas.get(i);
            if (mesa != null && mesa.estaDisponible) {
                return mesa;
            }
        }
        return null;
    }

    public void cambiarEstaEnMesa() {
        estaEnMesa = estaEnMesa == false;
    }

    public ArrayList<Plato> seleccionarPedido(Menu menu) {
//        System.out.println("cliente " + id + " seleccoinando pedido");
        ArrayList<Plato> platos = new ArrayList<>();
        ArrayList<Plato> entradas = menu.obtenerPlatosPorTipo(TipoPlato.ENTRADA);
        ArrayList<Plato> platosFuertes = menu.obtenerPlatosPorTipo(TipoPlato.PLATO_FUERTE);
        ArrayList<Plato> postres = menu.obtenerPlatosPorTipo(TipoPlato.POSTRE);

        boolean pedirEntrada = (int) (Math.random() * 2) == 1;
        boolean pedirUnPostre = (int) (Math.random() * 2) == 1;
        boolean pedirDosPostres = (int) (Math.random() * 2) == 1;
        
        platos.add(platosFuertes.get((int) (Math.random() * platosFuertes.size())));
        if (pedirEntrada) {
            platos.add(entradas.get((int) (Math.random() * entradas.size())));
        }
        if (pedirDosPostres) {
            platos.add(postres.get((int) (Math.random() * postres.size())));
            platos.add(postres.get((int) (Math.random() * postres.size())));
        } else if (pedirUnPostre) {
            platos.add(postres.get((int) (Math.random() * postres.size())));
        }
        System.out.println("cliente: pedido " + platos);
        return platos;
    }

    public synchronized void esperarPlato(Mesa mesa, ArrayList<Plato> platos, Mesero mesero) {
//        System.out.println("espetando -- " + ID_AUTO);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
//                    System.out.println("cliente " + id + " esperando plato " + platos);
                    int i = 0;
                    while (!platos.isEmpty()) {
                        Plato plato = platos.get(i);
                        try {
//                                                    System.out.println("cliente: plato  " + plato.nombre + "  " + plato.estaEntregado);
                            Thread.sleep(5);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.println("cliente-- " + plato);
                        if (plato.estaEntregado) {
                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            platos.remove(plato);
                            try {
                                //                            System.out.println("*********************cliente: plato " + plato.nombre);
//                            System.out.println("*clinete " + id + " consumir plato");
                                Thread.sleep(5);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            plato.calificar((int) (Math.random() * 6));
                            mesero.calificar((int) (Math.random() * 6));
                            mesero.recibirPropina(plato.precio * 0.1);
                            cambiarEstaEnMesa();
                        }
                        if (i == platos.size()) {
                            i = 0;
                        }
                    }
                    mesa.clientesSentados.remove(Cliente.this);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (mesa.clientesSentados.isEmpty()) {
//                        mesa.cambiarDisponibilidad();
//                        mesa.cambiarAtendida();
                        mesa.estaDisponible = true;
                        mesa.estaAtendida = false;
                        Mesero.mesasPendientes.remove(mesa);
                        mesa.desocuparMesa();
//                                break;
                    }
                }
            }
        }).start();
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + '}';
    }
}

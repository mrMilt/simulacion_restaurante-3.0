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
public class Cocinero extends Empleado {

    protected ArrayList<TipoPlato> habilidades;
    protected boolean preparandoPlato;

    private static int ID_AUTO = 1;

    public Cocinero(int jornadaTrabajo, int jornadaDescanso, int intervaloDescanso) {
        super(ID_AUTO++, jornadaTrabajo, jornadaDescanso, intervaloDescanso);
        this.preparandoPlato = false;
    }

    public boolean puedePrepararPlato(Plato plato) {
        for (int i = 0; i < habilidades.size(); i++) {
            TipoPlato habilidad = habilidades.get(i);
            if (habilidad == plato.tipoPlato) {
                return true;
            }
        }
        return false;
    }

    public synchronized void prepararPlato(ArrayList<Pedido> pedidos, Congelador congelador) {
//        System.out.println("fsdfsfd");
        new Thread(new Runnable() {
            @Override
            public void run() {
                long intervaloInicial = System.currentTimeMillis();
                long intervaloFinal = 0;
                long jornada = 0;
                while (jornada < jornadaTrabajo) {
//                    System.out.println("cocinero.:" + id +  " esperando conjelador");
                    while (!congelador.estaDisponible) {
                        System.out.println("cocinero " + id + " esperando el conjelador");
                    }
                    congelador.estaDisponible = false;
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Cocinero.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    congelador.estaDisponible = true;
                    if (!pedidos.isEmpty()) {
                        Pedido pedido = pedidos.get(0);
                        System.out.println("cocinero-: " + id + " pedido " + pedido);
                        ArrayList<Plato> platos = pedido.platos;
                        if (!platos.isEmpty()) {
                            Plato plato = platos.remove(0);
//                             System.out.println("cocinero " + id + "  " + plato);
                            if (!plato.estaListo) {
//                                System.out.println("obteniendo productos");
//                                System.out.println("cocinando");
                                plato.cambiarEstado();
                            }
                        }
                    }
                    intervaloFinal += System.currentTimeMillis();
                    if (((intervaloFinal - intervaloInicial) / 1000) >= intervaloDescanso * 60000) {
                        try {
                            // escala 1 h = 60000
                            Thread.sleep(60000 * jornadaDescanso / 60);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Cajero.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        jornada += intervaloDescanso;
                        intervaloInicial = System.currentTimeMillis();
                        intervaloFinal = 0;
                    }
                }
//                System.out.println("cocinero: termino jornada");
            }
        }).start();
    }
}

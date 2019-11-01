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

    public void prepararPlato(ArrayList<Pedido> pedidos, Congelador congelador) {
        System.out.println("fsdfsfd");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("cocinero:" + id +  " esperando conjelador");
                    while (!congelador.estaDisponible) {
                    }
                    if (!pedidos.isEmpty()) {
                        Pedido pedido = pedidos.get(0);
                        System.out.println("cocinero: " + id + " pedido " + pedido);
                        ArrayList<Plato> platos = pedido.platos;
                        if (!platos.isEmpty()) {
                            Plato plato = platos.remove(0);
                             System.out.println("cocinero " + id + "  " + plato);
                            if (!plato.estaListo) {
                                System.out.println("obteniendo productos");
                                System.out.println("cocinando");
                                plato.cambiarEstado();
                            }
                        }
                    }
                }
            }
        }).start();
    }
}

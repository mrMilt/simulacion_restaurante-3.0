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
public class Cajero extends Empleado {

    private static int ID_AUTO = 1;
    protected static int atendidos = 0;

    public Cajero(int jornadaTrabajo, int jornadaDescanso, int intervaloDescanso) {
        super(ID_AUTO++, jornadaTrabajo, jornadaDescanso, intervaloDescanso);
    }

    public void atenderClientes(ArrayList<Cliente> clientes) {
        new Thread(new Runnable() {
            @Override
            public void run() {// 0 1 2 3 4 5
                long intervaloInicial = System.currentTimeMillis();
                long intervaloFinal = 0;
                long jornada = 0;
                while (jornada < jornadaTrabajo) {  // a b c d e f
                    System.out.println("cajero: atendiendo clientes " + clientes);
                    int size = clientes.size();
                    for (int i = 0; i < clientes.size();) {
                        Cliente cliente = clientes.get(i);
                        System.out.println("lllllllllllllllllllllllllllllllllhfhghgfhfhfhhhhhhhhhhhhhhhhhhl cajero: cliente= " + cliente.getId() + "  " + cliente.estaEnMesa);
                        if (!cliente.estaEnMesa) {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Cajero.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.out.println("cajero: cliente* " + cliente.getId() + " atendido");
//                        clientes.remove(cliente);
                            atendidos++;
                        } else {
                            i++;
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

                }
//                System.out.println("cajero: termino jornada");
            }
        }).start();
    }
}

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
public class Cajero extends Empleado {
    
    private static int ID_AUTO = 1;
    
    public Cajero(int jornadaTrabajo, int jornadaDescanso, int intervaloDescanso) {
        super(ID_AUTO++, jornadaTrabajo, jornadaDescanso, intervaloDescanso);
    }
    
    public void atenderClientes(ArrayList<Cliente> clientes) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    for (int i = 0; i < clientes.size(); i++) {
                        Cliente cliente = clientes.get(i);
                        clientes.remove(cliente);
                    }
                }
            }
        }).start();
    }
}

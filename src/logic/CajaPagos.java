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
public class CajaPagos {
    
     private Cajero cajero;
     protected ArrayList<Cliente> colaCajero;

    public CajaPagos() {
        this.cajero = null;
        this.colaCajero = new ArrayList<>();
    }
    
    public void asignarCajero(int jornadaTrabajo, int jornadaDescanso, int intervaloDescanso) {
        this.cajero = new Cajero(jornadaTrabajo, jornadaDescanso, intervaloDescanso);
    }      
    
     public void agregarClientes(Cliente cliente) {
         colaCajero.add(cliente);
     }
     
     public void atenderClientes() {
          cajero.atenderClientes(colaCajero);
     }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulacionrestaurantev3;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.Cocinero;
import logic.Restaurante;
import logic.TipoPlato;

/**
 *
 * @author Milton
 */
public class SimulacionRestauranteV3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            Thread.sleep(0);
        } catch (InterruptedException ex) {
            Logger.getLogger(SimulacionRestauranteV3.class.getName()).log(Level.SEVERE, null, ex);
        }
        Restaurante  restaurante = new Restaurante();
        restaurante.agregarMesas(14);
        restaurante.agregarMesero(8, 10, 2);
        restaurante.agregarMesero(8, 10, 2);
        restaurante.agregarCocinero(8, 20, 2);
        restaurante.agregarCocinero(8, 10, 2);
        restaurante.agregarCajero(8, 10, 2);
        restaurante.agregarPlatoAlMenu("ensalada de frutas", TipoPlato.ENTRADA, 2000, 10);
        restaurante.agregarPlatoAlMenu("postre de chocolate", TipoPlato.POSTRE, 5000, 20);
        restaurante.agregarPlatoAlMenu("salmon a la parrilla", TipoPlato.PLATO_FUERTE, 15000, 30);
        restaurante.atenderMesas();
        restaurante.cocinar();
        restaurante.entregarPedidos();
        restaurante.atenderEnCaja();
        restaurante.esperarPedidos();
        
        restaurante.simular();
//        restaurante.desocuparMesas();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author Milton
 */
public class Cajero extends Empleado {
    
    private static int ID_AUTO = 1;
    
    public Cajero(int jornadaTrabajo, int jornadaDescanso, int intervaloDescanso) {
        super(ID_AUTO++, jornadaTrabajo, jornadaDescanso, intervaloDescanso);
    }
    
}

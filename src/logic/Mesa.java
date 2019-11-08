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
public class Mesa {

    protected int id;
    protected int numeroClientesMaximo;
    protected ArrayList<Cliente> clientesSentados;
    protected boolean estaDisponible;
    protected boolean estaAtendida;

    private static int ID_AUTO = 1;

    public Mesa(int numeroClientes) {
        this.id = ID_AUTO++;
        this.numeroClientesMaximo = numeroClientes;
        this.clientesSentados = new ArrayList<>();
        this.estaDisponible = true;
        this.estaAtendida = false;
    }

    public void agregarClientes(Cliente cliente) {
        clientesSentados.add(cliente);
    }

    public void cambiarDisponibilidad() {
        estaDisponible = estaDisponible != true;
    }

    public void cambiarAtendida() {
        estaAtendida = estaAtendida == false;
    }

    public void desocuparMesa() {
        clientesSentados.clear();
    }

    @Override
    public String toString() {
        return "Mesa{" + "id=" + id + "  " + estaDisponible;
    }

    public synchronized void desocupar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("sdfsdfs");
                    if (clientesSentados.isEmpty()) {
                        estaDisponible = true;
                        estaAtendida = false;
                        desocuparMesa();
                        System.out.println("estado mesa: " + id + "  " + estaDisponible);
//                        break;
                    }
                }
            }
        }).start();
    }
}

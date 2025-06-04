/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorioso;

/**
 *
 * @author fernandomaccio
 */
public class Paciente extends Thread {
    private final String nombre;
    private int prioridad;
    private final int tiempoAtencion;
    
    
    public Paciente(String nombre, int prioridad, int tiempoAtencion) {
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.tiempoAtencion = tiempoAtencion;
    }
    
    public void setPrioridad(int nuevaPrioridad) {
        this.prioridad = nuevaPrioridad;
    }

    @Override
    public void run() {
        System.out.println("[" + nombre + "] finalizado.");
    }
}

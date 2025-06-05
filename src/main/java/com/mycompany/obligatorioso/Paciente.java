/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorioso;

import java.time.LocalTime;
import java.util.concurrent.Semaphore;

/**
 *
 * @author fernandomaccio
 */
public class Paciente extends Thread {
    private final String nombre;
    private final LocalTime horaLlegada;
    private int prioridad;
    private final int tiempoAtencion;
    
    
    public Paciente(String nombre, LocalTime horaLlegada,int prioridad, int tiempoAtencion) {
        this.nombre = nombre;
        this.horaLlegada = horaLlegada;
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorioso;

import static com.mycompany.obligatorioso.ObligatorioSO.horaActual;
import static com.mycompany.obligatorioso.ObligatorioSO.semaforoEnfermeros;
import static com.mycompany.obligatorioso.ObligatorioSO.semaforoMedicos;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            semaforoMedicos.acquire();
            semaforoEnfermeros.acquire();
            Thread.sleep(500);
            horaActual = horaActual.plusMinutes(tiempoAtencion);
            System.out.println("[" + nombre + "] finalizado a las " + horaActual + "");
            semaforoMedicos.release();
            semaforoEnfermeros.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Paciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

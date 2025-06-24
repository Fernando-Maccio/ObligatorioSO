/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorioso;

import static com.mycompany.obligatorioso.ObligatorioSO.horaActual;
import static com.mycompany.obligatorioso.ObligatorioSO.recepcionista;
import static com.mycompany.obligatorioso.ObligatorioSO.semaforoConsultorios;
import java.time.LocalTime;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fernandomaccio
 */
public class Medico extends Thread {
    private final String nombre;
    private LocalTime horaEntrada;
    
    public Medico(String nombre){
        this.nombre = nombre;
    }
    
    @Override
    public void run() {
        horaEntrada = horaActual;
        System.out.println("Comenzo a trabajar: " + nombre);
        while(horaActual.isBefore(horaEntrada.plusHours(6))) {
            try {
                semaforoConsultorios.acquire();
                Paciente pacienteActual = recepcionista.getPaciente();
                if(pacienteActual != null) {
                    LocalTime horaFinAtencion = horaActual.plusMinutes(pacienteActual.getTiempoAtencion());
                    while(horaActual.isBefore(horaFinAtencion)){
                        Thread.sleep(100);
                    }
                    System.out.println("se termino de atender a " + pacienteActual.getNombre() + " a las " + horaActual.toString());
                }
                semaforoConsultorios.release();
            } catch (InterruptedException ex) {
                Logger.getLogger(Medico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Termino de trabajar: " + nombre);
    }
}

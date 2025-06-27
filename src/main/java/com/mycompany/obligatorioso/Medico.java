/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorioso;

import static com.mycompany.obligatorioso.ExportadorCSV.entradas;
import static com.mycompany.obligatorioso.ObligatorioSO.enfermeros;
import static com.mycompany.obligatorioso.ObligatorioSO.horaActual;
import static com.mycompany.obligatorioso.ObligatorioSO.recepcionista;
import static com.mycompany.obligatorioso.ObligatorioSO.semaforoConsultorios;
import static com.mycompany.obligatorioso.ObligatorioSO.semaforoEnfermeros;
import java.time.LocalTime;
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
                semaforoEnfermeros.acquire();
                Paciente pacienteActual = recepcionista.getPaciente();
                if(pacienteActual != null) {
                    LocalTime horaFinAtencion = horaActual.plusMinutes(pacienteActual.getTiempoAtencion());
                    if ("Analisis Clinico".equals(pacienteActual.getTipoAtencion())) {
                        Enfermero enfermeroActual = enfermeros.removeFirst();
                        enfermeroActual.atender(pacienteActual, horaFinAtencion);
                    } else {
                        if("Carnet de Salud".equals(pacienteActual.getTipoAtencion()) && !pacienteActual.hasInformeOdontologico()) {
                            System.out.println(pacienteActual.getNombre() + " no trajo informe odontologico");
                            entradas.add(new EntradaCSV(pacienteActual.getNombre(), pacienteActual.getHoraLlegada(), pacienteActual.getTipoAtencion(), null, horaActual, "No trajo informe odontologico"));
                        } else {
                            while(horaActual.isBefore(horaFinAtencion)){
                                Thread.sleep(10);
                            }
                            System.out.println("El medico " + nombre + " termino de atender a " + pacienteActual.getNombre() + " a las " + horaActual.toString());
                            entradas.add(new EntradaCSV(pacienteActual.getNombre(), pacienteActual.getHoraLlegada(), pacienteActual.getTipoAtencion(), horaActual.minusMinutes(pacienteActual.getTiempoAtencion()), horaActual, "Atendido por el medico " + nombre));
                        }
                        semaforoEnfermeros.release();
                    }
                } else {
                    semaforoEnfermeros.release();
                }
                semaforoConsultorios.release();
            } catch (InterruptedException ex) {
                Logger.getLogger(Medico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Termino de trabajar: " + nombre);
    }
}

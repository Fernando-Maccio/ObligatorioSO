/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorioso;

import static com.mycompany.obligatorioso.ObligatorioSO.enfermeros;
import static com.mycompany.obligatorioso.ObligatorioSO.horaActual;
import static com.mycompany.obligatorioso.ObligatorioSO.semaforoEnfermeros;
import static com.mycompany.obligatorioso.ObligatorioSO.semaforoSalaEnfermeria;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fernandomaccio
 */
public class Enfermero extends Thread {
    private final String nombre;
    private LocalTime horaEntrada;
    private boolean atendiendo = false;
    
    public Enfermero(String nombre){
        this.nombre = nombre;
    }
    
    @Override
    public void run() {
        horaEntrada = horaActual;
        System.out.println("Comenzo a trabajar: " + nombre);
        while(horaActual.isBefore(horaEntrada.plusHours(6)) || atendiendo) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Enfermero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Termino de trabajar: " + nombre);
        enfermeros.remove(this);
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void atender(Paciente pacienteActual, LocalTime horaFinAtencion) throws InterruptedException {
        semaforoSalaEnfermeria.acquire();
        atendiendo = true;
        while(horaActual.isBefore(horaFinAtencion)){
            Thread.sleep(100);
        }
        System.out.println("El enfermero " + nombre + " termino de atender a " + pacienteActual.getNombre() + " a las " + horaActual.toString());
        enfermeros.add(this);
        semaforoEnfermeros.release();
        atendiendo = false;
        semaforoSalaEnfermeria.release();
    }
}

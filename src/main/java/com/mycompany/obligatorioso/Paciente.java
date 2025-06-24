/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorioso;

import java.time.LocalTime;

/**
 *
 * @author fernandomaccio
 */
public class Paciente {
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
    
    public String getNombre() {
        return nombre;
    }
    
    public LocalTime getHoraLlegada(){
        return horaLlegada;
    }
    
    public int getPrioridad() {
        return prioridad;
    }
    
    public void aumentarPrioridad() {
        this.prioridad += 1;
    }
    
    public int getTiempoAtencion() {
        return tiempoAtencion;
    }
}

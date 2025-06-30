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
    private final String tipoAtencion;
    private final int tiempoAtencion;
    private final boolean informeOdontologico;
    
    
    public Paciente(String nombre, LocalTime horaLlegada,int prioridad, String tipoAtencion, boolean informeOdontologico) {
        this.nombre = nombre;
        this.horaLlegada = horaLlegada;
        this.prioridad = prioridad;
        this.tipoAtencion = tipoAtencion;
        int tmpTiempoAtencion = 0;
        switch (tipoAtencion) {
            case "Emergencia" ->
                tmpTiempoAtencion = 15;
            case "Control General" ->
                tmpTiempoAtencion = 5;
            case "Odontologia" ->
                tmpTiempoAtencion = 0;
            case "Analisis Clinico" ->
                tmpTiempoAtencion = 10;
            case "Carnet de Salud" ->
                tmpTiempoAtencion = 10;
            default ->
                System.out.println("Tipo de atencion inválido");
        }
        this.tiempoAtencion = tmpTiempoAtencion;
        this.informeOdontologico = informeOdontologico;
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
    
    public void setPrioridad(int nuevaPrioridad) {
        prioridad = nuevaPrioridad;
    }
    
    public void aumentarPrioridad() {
        this.prioridad += 1;
    }
    
    public String getTipoAtencion() {
        return tipoAtencion;
    }
    
    public int getTiempoAtencion() {
        return tiempoAtencion;
    }
    
    public boolean hasInformeOdontologico() {
        return informeOdontologico;
    }
}

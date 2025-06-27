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
public class EntradaCSV {
    private final String nombre;
    private final LocalTime horaLlegada;
    private final String tipoAtencion;
    private final LocalTime horaAtencion;
    private final LocalTime horaSalida;
    private final String mensaje;
    
    
    public EntradaCSV(String nombre, LocalTime horaLlegada, String tipoAtencion, LocalTime horaAtencion, LocalTime horaSalida, String mensaje) {
        this.nombre = nombre;
        this.horaLlegada = horaLlegada;
        this.tipoAtencion = tipoAtencion;
        this.horaAtencion = horaAtencion;
        this.horaSalida = horaSalida;
        this.mensaje = mensaje;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getHoraLlegada(){
        return horaLlegada.toString();
    }
    
    public String getTipoAtencion() {
        return tipoAtencion;
    }
    
    public String getHoraAtencion(){
        return horaAtencion == null ? "" : horaAtencion.toString();
    }
    
    public String getHoraSalida(){
        return horaSalida.toString();
    }
    
    public String getMensaje(){
        return mensaje;
    }
}

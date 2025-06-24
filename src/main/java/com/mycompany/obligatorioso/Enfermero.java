/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorioso;

import static com.mycompany.obligatorioso.ObligatorioSO.horaActual;
import java.time.LocalTime;

/**
 *
 * @author fernandomaccio
 */
public class Enfermero extends Thread {
    private final String nombre;
    private LocalTime horaEntrada;
    
    public Enfermero(String nombre){
        this.nombre = nombre;
    }
    
    @Override
    public void run() {
        while(horaActual.isBefore(horaEntrada.plusHours(6))) {
            System.out.println("No se pudo leer el archivo");
        }
    }
}

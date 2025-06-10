/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.obligatorioso;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 *
 * @author fernandomaccio
 */
public class ObligatorioSO {
    public static LocalTime horaActual = LocalTime.of(8, 0);
    public static ArrayList<Medico> medicos = new ArrayList<>();
    public static ArrayList<Enfermero> enfermeros = new ArrayList<>();
    public static Semaphore semaforoMedicos;
    public static Semaphore semaforoEnfermeros;

    public static void main(String[] args) {
        Recepcionista recepcionista = new Recepcionista();
        Object[] atenciones = recepcionista.abrirCentro();
        
        medicos.add(new Medico("nombre medico"));
        enfermeros.add(new Enfermero("nombre enfermero"));
        
        semaforoMedicos = new Semaphore(medicos.size());
        semaforoEnfermeros  = new Semaphore(enfermeros.size());
        
        ArrayList<Thread> emergencias = (ArrayList<Thread>) atenciones[0];
        ArrayList<Thread> consultas = (ArrayList<Thread>) atenciones[1];
        
        while(!emergencias.isEmpty() || !consultas.isEmpty()) {
            if(!emergencias.isEmpty()) {
                Thread paciente = emergencias.getFirst();
                paciente.start();
                emergencias.removeFirst();
            } else {
                Thread paciente = consultas.getFirst();
                paciente.start();
                consultas.removeFirst();
            }
        }
    }
}

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
    public static Recepcionista recepcionista = new Recepcionista();
    public static Semaphore semaforoTomandoPaciente = new Semaphore(1);
    public static Semaphore semaforoConsultorios = new Semaphore(2);
    public static Semaphore semaforoSalaEnfermeria = new Semaphore(1);
    public static Semaphore semaforoEnfermeros;
    public static ArrayList<Paciente> colaEmergencias = new ArrayList<>();
    public static ArrayList<Paciente> colaConsultas = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        recepcionista.abrirCentro();
        
        medicos.add(new Medico("primer medico"));
        enfermeros.add(new Enfermero("primer enfermero"));
        
        semaforoEnfermeros  = new Semaphore(enfermeros.size());
        
        for (Medico medico : medicos) {
            medico.start();
        }
        for (Enfermero enfermero : enfermeros) {
            enfermero.start();
        }
        while(horaActual.isBefore(LocalTime.of(20, 0))) {
            recepcionista.ingresarPacientes();
            System.out.println(horaActual);
            Thread.sleep(500);
            horaActual = horaActual.plusMinutes(5);
            if (horaActual.equals(LocalTime.of(14, 0))) {
                medicos = new ArrayList<>();
                enfermeros = new ArrayList<>();
                medicos.add(new Medico("tercer medico"));
                enfermeros.add(new Enfermero("tercer enfermero"));
                
                for (Medico medico : medicos) {
                    medico.start();
                }
                for (Enfermero enfermero : enfermeros) {
                    enfermero.start();
                }
            }
        }
    }
}

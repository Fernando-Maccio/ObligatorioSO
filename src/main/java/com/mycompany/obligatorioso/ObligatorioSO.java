/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.obligatorioso;

import static com.mycompany.obligatorioso.ExportadorCSV.entradas;
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
        // medicos.add(new Medico("segundo medico"));
        enfermeros.add(new Enfermero("primer enfermero"));
        //enfermeros.add(new Enfermero("segundo enfermero"));
        
        semaforoEnfermeros  = new Semaphore(enfermeros.size());
        
        for (Medico medico : medicos) {
            medico.start();
        }
        for (Enfermero enfermero : enfermeros) {
            enfermero.start();
        }
        while(horaActual.plusMinutes(1).isBefore(LocalTime.of(20, 0))) {
            recepcionista.ingresarPacientes();
            System.out.println(horaActual);
            Thread.sleep(50);
            horaActual = horaActual.plusMinutes(5);
            if (horaActual.equals(LocalTime.of(14, 0))) {
                medicos = new ArrayList<>();
                enfermeros = new ArrayList<>();
                medicos.add(new Medico("tercer medico"));
                // medicos.add(new Medico("cuarto medico"));
                enfermeros.add(new Enfermero("tercer enfermero"));
                // enfermeros.add(new Enfermero("cuarto enfermero"));
                
                for (Medico medico : medicos) {
                    medico.start();
                }
                for (Enfermero enfermero : enfermeros) {
                    enfermero.start();
                }
            }
        }
        ArrayList<Paciente> pacientesSinAtender = new ArrayList<>(colaEmergencias);
        pacientesSinAtender.addAll(colaConsultas);
        for (Paciente paciente : pacientesSinAtender) {
            System.out.println("No se pudo atender a" + paciente.getNombre());
            entradas.add(new EntradaCSV(paciente.getNombre(), paciente.getHoraLlegada(), paciente.getTipoAtencion(), null, horaActual, "No fue atendido"));
        }
        while(medicos.stream().anyMatch(Thread::isAlive)) {
            System.out.println(horaActual);
            Thread.sleep(50);
            horaActual = horaActual.plusMinutes(5);
        }
        Thread.sleep(100);
        ExportadorCSV.exportarPacientes();
    }
}

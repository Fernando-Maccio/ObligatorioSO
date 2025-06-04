/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.obligatorioso;

import java.util.ArrayList;

/**
 *
 * @author fernandomaccio
 */
public class ObligatorioSO {

    public static void main(String[] args) {
        Recepcionista recepcionista = new Recepcionista();
        Object[] apertura = recepcionista.abrirCentro();
        
        ArrayList<Medico> medicos = (ArrayList<Medico>) apertura[0];
        ArrayList<Enfermero> enfermeros = (ArrayList<Enfermero>) apertura[1];
        ArrayList<Thread> emergencias = (ArrayList<Thread>) apertura[2];
        ArrayList<Thread> consultas = (ArrayList<Thread>) apertura[3];
        
        for (Thread emergencia : emergencias) {
            emergencia.start();
        }
        for (Thread consulta : consultas) {
            consulta.start();
        }
    }
}

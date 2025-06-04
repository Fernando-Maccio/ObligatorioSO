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
        Object[] atenciones = recepcionista.abrirCentro();
        ArrayList<Thread> emergencias = (ArrayList<Thread>) atenciones[0];
        ArrayList<Thread> consultas = (ArrayList<Thread>) atenciones[1];
        for (Thread emergencia : emergencias) {
            emergencia.start();
        }
        for (Thread consulta : consultas) {
            consulta.start();
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorioso;

import static com.mycompany.obligatorioso.ObligatorioSO.colaConsultas;
import static com.mycompany.obligatorioso.ObligatorioSO.colaEmergencias;
import static com.mycompany.obligatorioso.ObligatorioSO.horaActual;
import static com.mycompany.obligatorioso.ObligatorioSO.semaforoEnfermeros;
import static com.mycompany.obligatorioso.ObligatorioSO.semaforoTomandoPaciente;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import org.xml.sax.SAXException;

/**
 *
 * @author fernandomaccio
 */
public class Recepcionista {

    ArrayList<Paciente> emergencias = new ArrayList<>();
    ArrayList<Paciente> consultas = new ArrayList<>();

    public void abrirCentro() {
        try {
            File archivo = new File("pacientes.xml");

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(archivo);
            doc.getDocumentElement().normalize();

            NodeList lista = doc.getElementsByTagName("paciente");

            for (int i = 0; i < lista.getLength(); i++) {
                Node nodo = lista.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;
                    String nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();
                    String tipoAtencion = elemento.getElementsByTagName("tipoAtencion").item(0).getTextContent();
                    String horaLlegada = elemento.getElementsByTagName("horaLlegada").item(0).getTextContent();
                    String prioridadInicial = "1";

                    NodeList nodos = elemento.getElementsByTagName("prioridadInicial");
                    if (nodos.getLength() > 0 && nodos.item(0) != null) {
                        prioridadInicial = nodos.item(0).getTextContent();
                    }

                    switch (tipoAtencion) {
                        case "Emergencia" ->
                            emergencias.add(new Paciente(nombre, LocalTime.parse(horaLlegada), Integer.parseInt(prioridadInicial), 15));
                        case "Control General" ->
                            consultas.add(new Paciente(nombre, LocalTime.parse(horaLlegada), 1, 5));
                        case "Odontologia" ->
                            consultas.add(new Paciente(nombre, LocalTime.parse(horaLlegada), 1, 0));
                        case "Analisis Clinico" ->
                            consultas.add(new Paciente(nombre, LocalTime.parse(horaLlegada), 1, 10));
                        default ->
                            System.out.println("Tipo de atencion inválido");
                    }
                }
            }
        } catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
            System.out.println("No se pudo leer el archivo");
        }
    }

    public void ingresarPacientes() throws InterruptedException {
        colaEmergencias.forEach(p -> {
            if (p.getHoraLlegada().getMinute() % 10 == 0) {
                if(horaActual.getMinute() % 10 == 0) {
                    p.aumentarPrioridad();
                }
            } else {
                if(horaActual.getMinute() % 10 != 0) {
                    p.aumentarPrioridad();
                }
            }
        });
        
        colaConsultas.forEach(p -> {
            if (p.getHoraLlegada().getMinute() % 10 == 0) {
                if(horaActual.getMinute() % 10 == 0) {
                    p.aumentarPrioridad();
                }
            } else {
                if(horaActual.getMinute() % 10 != 0) {
                    p.aumentarPrioridad();
                }
            }
        });
        
        emergencias.forEach(p -> {
            if (p.getHoraLlegada().equals(horaActual)) colaEmergencias.add(p);
        });
        consultas.forEach(p -> {
            if (p.getHoraLlegada().equals(horaActual)) colaConsultas.add(p);
        });
        
        semaforoTomandoPaciente.acquire();
        colaEmergencias.sort(Comparator.comparingInt(Paciente::getPrioridad).reversed());
        colaConsultas.sort(Comparator.comparingInt(Paciente::getPrioridad).reversed());
        semaforoTomandoPaciente.release();
    }
    
    public Paciente getPaciente() throws InterruptedException {
        Paciente pacienteActual = null;
        semaforoTomandoPaciente.acquire();
        if(!colaEmergencias.isEmpty()){
            pacienteActual = colaEmergencias.getFirst();
            colaEmergencias.removeFirst();
        } else if(!colaConsultas.isEmpty()) {
            pacienteActual = colaConsultas.getFirst();
            colaConsultas.removeFirst();
        }
        semaforoTomandoPaciente.release();
        
        return pacienteActual;
    }
}

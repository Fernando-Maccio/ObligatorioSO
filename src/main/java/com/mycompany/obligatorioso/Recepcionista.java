/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorioso;

import static com.mycompany.obligatorioso.ExportadorCSV.entradas;
import static com.mycompany.obligatorioso.ObligatorioSO.colaConsultas;
import static com.mycompany.obligatorioso.ObligatorioSO.colaEmergencias;
import static com.mycompany.obligatorioso.ObligatorioSO.horaActual;
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

    private final ArrayList<Paciente> emergencias = new ArrayList<>();
    private final ArrayList<Paciente> consultas = new ArrayList<>();

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
                    boolean informeOdontologico = false;

                    NodeList nodosPrioridadInicial = elemento.getElementsByTagName("prioridadInicial");
                    if (nodosPrioridadInicial.getLength() > 0 && nodosPrioridadInicial.item(0) != null) {
                        prioridadInicial = nodosPrioridadInicial.item(0).getTextContent();
                    }
                    
                    NodeList nodosInformeOdontologico = elemento.getElementsByTagName("informeOdontologico");
                    if (nodosInformeOdontologico.getLength() > 0 && nodosInformeOdontologico.item(0) != null) {
                        informeOdontologico = Boolean.parseBoolean(nodosInformeOdontologico.item(0).getTextContent());
                    }
                    
                    emergencias.add(new Paciente(nombre, LocalTime.parse(horaLlegada), Integer.parseInt(prioridadInicial), tipoAtencion, informeOdontologico));
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
            if(p.getPrioridad() == 6) {
                colaEmergencias.add(p);
            }
        });
        colaConsultas.removeIf(p -> p.getPrioridad() == 6);
        
        emergencias.forEach(p -> {
            if (p.getHoraLlegada().equals(horaActual)) {
                if ("Odontologia".equals(p.getTipoAtencion())) {
                    System.out.println(p.getNombre() + " enviado al odontologo");
                    entradas.add(new EntradaCSV(p.getNombre(), p.getHoraLlegada(), p.getTipoAtencion(), horaActual, "Enviado al odontologo"));
                } else colaEmergencias.add(p);
            }
        });
        consultas.forEach(p -> {
            if (p.getHoraLlegada().equals(horaActual)) {
                if ("Odontologia".equals(p.getTipoAtencion())) {
                    System.out.println(p.getNombre() + " enviado al odontologo");
                    entradas.add(new EntradaCSV(p.getNombre(), p.getHoraLlegada(), p.getTipoAtencion(), horaActual, "Enviado al odontologo"));
                } else colaConsultas.add(p);
            }
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

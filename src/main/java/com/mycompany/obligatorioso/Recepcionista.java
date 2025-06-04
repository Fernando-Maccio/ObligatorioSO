/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorioso;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.xml.sax.SAXException;

/**
 *
 * @author fernandomaccio
 */
public class Recepcionista {
    public Object[] abrirCentro(){
        ArrayList<Thread> emergencias = new ArrayList<>();
        ArrayList<Thread> consultas = new ArrayList<>();
        
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

                    switch (tipoAtencion) {
                        case "Emergencia":
                            emergencias.add(new Paciente(nombre, 1, 1));
                            break;
                        case "Control General":
                            consultas.add(new Paciente(nombre, 1, 1));
                            break;
                        case "Odontologia":
                            consultas.add(new Paciente(nombre, 1, 1));
                            break;
                        case "Analisis Clinico":
                            consultas.add(new Paciente(nombre, 1, 1));
                            break;
                        default:
                            System.out.println("Tipo de atencion inválido");
                    }
                }
            }
        } catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
            System.out.println("No se pudo leer el archivo");
        }
        
        return new Object[]{emergencias, consultas};
    }
}

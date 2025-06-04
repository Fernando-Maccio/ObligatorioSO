/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.obligatorioso;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import org.xml.sax.SAXException;

/**
 *
 * @author fernandomaccio
 */
public class Recepcionista {
    public void abrirCentro(){
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

                    Thread paciente = new Paciente(nombre, 1, 1);

                    paciente.start();
                }
            }

        } catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
            System.out.println("Algo salio mal");
        }
    }
}

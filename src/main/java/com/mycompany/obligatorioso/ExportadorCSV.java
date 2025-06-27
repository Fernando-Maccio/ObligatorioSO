package com.mycompany.obligatorioso;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExportadorCSV {
    public static ArrayList<EntradaCSV> entradas = new ArrayList<>();

    public static void exportarPacientes() {
        String rutaArchivo = "datos_simulacion.csv";
        try (FileWriter writer = new FileWriter(rutaArchivo)) {

            writer.append("Nombre,Hora de Llegada,Tipo de Atención,Hora de Atencion,Hora de Salida,Mensaje\n");

            for (EntradaCSV e : entradas) {
                writer.append(e.getNombre()).append(",")
                      .append(e.getHoraLlegada()).append(",")
                      .append(e.getTipoAtencion()).append(",")
                      .append(e.getHoraAtencion()).append(",")
                      .append(e.getHoraSalida()).append(",")
                      .append(e.getMensaje()).append("\n");
            }

            System.out.println("✅ CSV generado correctamente en: " + rutaArchivo);

        } catch (IOException e) {
        }
    }
}

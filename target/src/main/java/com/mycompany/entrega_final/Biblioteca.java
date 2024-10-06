/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entrega_final;

/**
 *
 * @author javie
 */

import java.io.*;
import java.util.*;

public class Biblioteca {
    private Map<Integer, Estante> estantes;

    public Biblioteca() {
        this.estantes = new HashMap<>();
    }

    public void agregarEstante(Estante estante) {
        estantes.putIfAbsent(estante.getCodigo(), estante);
    }

    public void agregarLibroAEstante(int codigoEstante, Libro libro) {
        Estante estante = estantes.get(codigoEstante);
        if (estante != null) {
            estante.agregarLibro(libro);
        } else {
            System.out.println("Estante con código " + codigoEstante + " no encontrado.");
        }
    }

    public void cargarLibrosDesdeArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            br.readLine(); // Esta línea se encarga de omitir los encabezados

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                // Asegúrate de que haya suficientes elementos en el arreglo datos
                if (datos.length < 8) {
                    System.out.println("Error en la línea: " + linea);
                    continue; // Salta a la siguiente línea si los datos son insuficientes
                }
                // Crear el libro
                Libro libro = new Libro(datos[0], datos[1], datos[2], datos[3],Double.parseDouble(datos[4]), Integer.parseInt(datos[5]));
                System.out.println("Libro");

                // Verificar o crear el estante automáticamente
                int codigoEstante;
                try {
                    codigoEstante = Integer.parseInt(datos[6]);
                } catch (NumberFormatException e) {
                    System.out.println("Código de estante inválido en la línea: " + linea);
                    continue; // Salta a la siguiente línea si el código de estante es inválido
                }
            
                String nombreEstante = datos[7];
                Estante estante = estantes.get(codigoEstante);
        
                if (estante == null) {
                    estante = new Estante(codigoEstante, nombreEstante);
                    agregarEstante(estante);
                    System.out.println("Estante creado: " + estante.getNombre());
                }
        
                // Agregar el libro al estante correspondiente
                estante.agregarLibro(libro);
                System.out.println("Libro agregado: " + libro.getTitulo() + " al estante " + estante.getNombre());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void mostrarContenido() {
        for (Estante estante : estantes.values()) {
            System.out.println(estante);
        }
    }
    
    public void mostrarContenido1() {
        if (estantes.isEmpty()) {
            System.out.println("No hay estantes disponibles.");
            return; // Sale del método si no hay estantes
        }

        // Itera sobre cada estante en el mapa de estantes
        for (Estante estante : estantes.values()) {
            System.out.println("Estante: " + estante.getNombre() + " (Código: " + estante.getCodigo() + ")");
            // Itera sobre los libros en cada estante y los imprime
            for (Libro libro : estante.getLibros()) {
                System.out.println("  - " + libro.getTitulo() + " por " + libro.getAutor() + " (Género: " + libro.getGenero() + ", Puntaje: " + libro.getPuntaje() + ", Cantidad: " + libro.getCantidad() + ")");
            }
        }
    }


}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entrega_final;

/**
 *
 * @author javie
 */
import java.util.*;
import java.io.*;

public class Biblioteca {
    private Map<String, Libro> libros; // Mapa para almacenar los libros con su código como clave
    private Map<String, List<Libro>> librosPorGenero; // Mapa para almacenar listas de libros por género
    private String archivoCSV = "Libros";

    public Biblioteca() {
        libros = new HashMap<>(); // Inicializar el mapa de libros
        librosPorGenero = new HashMap<>(); // Inicializar el mapa de libros por género
        cargarLibrosDesdeCSV(archivoCSV); // Cargar libros al iniciar
    }

    // Método para leer libros desde un archivo CSV
    public void cargarLibrosDesdeCSV(String nombreArchivo) {
        String linea;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(nombreArchivo));
            // Leer y descartar la primera línea que contiene los nombres de las columnas
            br.readLine(); // Leer y descartar la cabecera

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 6) { // Asegurarse de que la línea tiene 6 campos
                    String codigo = datos[0].trim();
                    String titulo = datos[1].trim();
                    String autor = datos[2].trim();
                    String genero = datos[3].trim();
                    double puntaje = Double.parseDouble(datos[4].trim());
                    int cantidad = Integer.parseInt(datos[5].trim());

                    Libro libro = new Libro(codigo, titulo, autor, genero, puntaje, cantidad);

                    // Agregar el libro al mapa de libros
                    libros.put(codigo, libro);

                    // Agregar el libro al mapa de libros por género
                    librosPorGenero.computeIfAbsent(genero, k -> new ArrayList<>()).add(libro);
                }
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir datos numéricos: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar el BufferedReader: " + e.getMessage());
                }
            }
        }
    }

    public void agregarLibro() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el código del libro: ");
        String codigo = scanner.nextLine();

        System.out.print("Ingrese el nombre del libro: ");
        String titulo = scanner.nextLine();

        System.out.print("Ingrese el autor del libro: ");
        String autor = scanner.nextLine();

        System.out.print("Ingrese el género del libro: ");
        String genero = scanner.nextLine();

        System.out.print("Ingrese el puntaje del libro: ");
        double puntaje = Double.parseDouble(scanner.nextLine());

        System.out.print("Ingrese la cantidad de ejemplares del libro: ");
        int cantidad = Integer.parseInt(scanner.nextLine());

        Libro nuevoLibro = new Libro(codigo, titulo, autor, genero, puntaje, cantidad);

        // Agregar el libro al mapa de libros
        libros.put(codigo, nuevoLibro);

        // Agregar el libro al mapa de libros por género
        librosPorGenero.computeIfAbsent(genero, k -> new ArrayList<>()).add(nuevoLibro);

        // Escribir en el archivo CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoCSV, true))) {
            bw.newLine();
            bw.write(nuevoLibro.datos());
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir el archivo: " + e.getMessage());
        }

        System.out.println("Libro agregado y guardado exitosamente.");
    }

    public void mostrarLibros() {
        List<Libro> listaLibros = new ArrayList<>(libros.values());
        // Ordenar por el código del libro
        listaLibros.sort(Comparator.comparing(Libro::getCodigo));

        for (Libro libro : listaLibros) {
            mostrarLibro(libro);
        }
    }
    
    // Método original que muestra la información del libro
    public void mostrarLibro(Libro libro) {
        if (libro != null) {
            System.out.println("Código: " + libro.getCodigo());
            System.out.println("Nombre: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor());
            System.out.println("Género: " + libro.getGenero());
            System.out.println("Puntaje: " + libro.getPuntaje());
            System.out.println("Cantidad: " + libro.getCantidad());
            System.out.println();
        } else {
            System.out.println("Libro no encontrado.");
        }
    }

    // Sobrecarga 1: Buscar libro por código
    public void buscarLibro(String codigo) {
        Libro libro = libros.get(codigo);
        if (libro != null) {
            mostrarLibro(libro);
        } else {
            System.out.println("Libro no encontrado con el código: " + codigo);
        }
    }


    // Sobrecarga 2: Buscar libro por género
    public void buscarLibro(String genero, boolean porGenero) {
        if (porGenero) {
            boolean generoEncontrado = false;
            // Iteramos sobre el conjunto de entradas del mapa
            for (Map.Entry<String, List<Libro>> entry : librosPorGenero.entrySet()) {
                String generoActual = entry.getKey();
                List<Libro> librosEnGenero = entry.getValue();

                // Comparar el género actual con el que buscamos
                if (generoActual.equalsIgnoreCase(genero)) {
                    System.out.println("Libros encontrados en el género: " + generoActual);
                    for (Libro libro : librosEnGenero) {
                        mostrarLibro(libro);
                    }
                    generoEncontrado = true;
                    break;
                }
            }
        
            if (!generoEncontrado) {
                System.out.println("No se encontraron libros para el género: " + genero);
            }
        }
    }

    public Libro getLibro(String codigo) {
        return libros.get(codigo);  // Devuelve el libro asociado al código
    }
}


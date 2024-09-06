/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.entrega_final;

import java.io.*;

/**
 *
 * @author javie
 */
public class Entrega_Final {

    public static void main(String[] args) {
        BufferedReader usuario = new BufferedReader(new InputStreamReader(System.in));
        Biblioteca biblioteca = new Biblioteca();

        // Mostrar los libros cargados
        biblioteca.mostrarLibros();

        int opcion = 0;  // Inicializa la variable opcion

        do {
            try {
                // Muestra el menú
                System.out.println("Menú:");
                System.out.println("1. Insercion libro");
                System.out.println("2. Edicion libro");
                System.out.println("3. Eliminar libro");
                System.out.println("4. Reporte de libros");
                System.out.println("5. Limpiar pantalla");
                System.out.println("6. Salir sistema");
                System.out.print("Ingrese una opcion: ");

                // Lee la opción del usuario
                opcion = Integer.parseInt(usuario.readLine());

                // Ejecuta acciones basadas en la opción seleccionada
                switch (opcion) {
                    case 1:
                        biblioteca.agregarLibro();
                        break;
                    case 2:
                        //editarLibro(usuario, biblioteca);
                        // Aquí iría la lógica para editar un libro
                        System.out.println("Funcionalidad de editar aún no implementada.");
                        break;
                    case 3:
                        // Aquí iría la lógica para eliminar un libro
                        System.out.println("Funcionalidad de eliminación aún no implementada.");
                        break;
                    case 4:
                        // Submenú para búsqueda
                        System.out.println("Buscar libro por:");
                        System.out.println("1. Código");
                        System.out.println("2. Género");
                        System.out.print("Seleccione una opción: ");

                        int opcionBusqueda = Integer.parseInt(usuario.readLine());

                        if (opcionBusqueda == 1) {
                            System.out.print("Ingrese el Código del libro: ");
                            String codigo = usuario.readLine();
                            biblioteca.buscarLibro(codigo);  // Llama a la función para buscar por código
                        } else if (opcionBusqueda == 2) {
                            System.out.print("Ingrese el Género del libro: ");
                            String genero = usuario.readLine();
                            biblioteca.buscarLibro(genero,true);  // Llama a la función para buscar por género
                        } else {
                            System.out.println("Opción no válida.");
                        }
                        break;
                    case 5:
                        limpiarPantalla();
                        System.out.println("Pantalla limpia");
                        break;
                    case 6:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (IOException e) {
                System.out.println("Error de entrada/salida: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        } while (opcion != 6);
    }

    private static void limpiarPantalla() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    
    private static void editarLibro(BufferedReader usuario, Biblioteca biblioteca) throws IOException {
        System.out.print("Ingrese el código del libro que desea editar: ");
        String codigo = usuario.readLine();

        Libro libro = biblioteca.getLibro(codigo);
        if (libro != null) {
            System.out.println("Libro encontrado:");
            System.out.println(libro);

            System.out.println("Seleccione el atributo a editar:");
            System.out.println("1. Puntaje");
            System.out.println("2. Cantidad");
            System.out.print("Ingrese su opción: ");

            int opcionEdicion = Integer.parseInt(usuario.readLine());

            switch (opcionEdicion) {
                case 1:
                    System.out.print("Ingrese el nuevo puntaje: ");
                    double nuevoPuntaje = Double.parseDouble(usuario.readLine());
                    libro.actualizar(nuevoPuntaje);
                    break;
                case 2:
                    System.out.print("Ingrese la nueva cantidad: ");
                    int nuevaCantidad = Integer.parseInt(usuario.readLine());
                    libro.setCantidad(nuevaCantidad);
                    break;
                default:
                    System.out.println("Opción no válida.");
                    return;
            }

            System.out.println("Libro actualizado:");
            System.out.println(libro);

            // Actualiza el archivo CSV
            // biblioteca.actualizarArchivo();
        } else {
            System.out.println("Libro no encontrado con el código: " + codigo);
        }
    }
}
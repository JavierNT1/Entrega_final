/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.entrega_final;

/**
 *
 * @author javie
 */

public class Entrega_Final {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        // Cargar los estantes y libros desde el archivo al iniciar
        biblioteca.cargarLibrosDesdeArchivo("Libros.csv");

        // Mostrar el contenido de la biblioteca
        biblioteca.mostrarContenido();
        biblioteca.mostrarContenido1();

    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.entrega_final;

/**
 *
 * @author javie
 */

import java.util.Scanner;

public class Entrega_Final {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);

        // Cargar datos desde los archivos al iniciar
        biblioteca.cargarLibrosDesdeArchivo("Libros.csv");
        biblioteca.cargarClientesDesdeArchivo("Clientes.csv");

        int opcion;
        do {
            System.out.println("--- Menú de Biblioteca ---");
            System.out.println("1. Mostrar contenido");
            System.out.println("2. Insercion libro");
            System.out.println("3. Modificar libro");
            System.out.println("4. Eliminar libro");
            System.out.println("5. Registrar cliente");
            System.out.println("6. Registrar venta");
            System.out.println("7. Reporte");
            System.out.println("8. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    biblioteca.mostrarContenido();
                    break;
                    
                case 2:
                    biblioteca.agregarLibro();
                    break; 
                   
                case 3:
                    System.out.print("Ingresa el código del libro a modificar: ");
                    String codigoLibroModificar = scanner.nextLine();
                    biblioteca.modificarLibro(codigoLibroModificar);
                    biblioteca.guardarLibrosEnArchivo("Libros.csv");
                    break;
                case 4:
                    System.out.print("Ingresa el código del libro a eliminar: ");
                    String codigoLibroEliminar = scanner.nextLine();
                    biblioteca.eliminarLibro(codigoLibroEliminar);
                    biblioteca.guardarLibrosEnArchivo("Libros.csv");
                    break;
                case 5:
                    System.out.print("Ingresa el nombre del cliente: ");
                    String nombreCliente = scanner.nextLine();
                    System.out.print("Ingresa el ID del cliente: ");
                    String idCliente = scanner.next(); 
                    scanner.nextLine(); // Limpiar el buffer
                    biblioteca.registrarCliente(new Cliente(idCliente, nombreCliente));
                    biblioteca.guardarClientesEnArchivo("Clientes.csv");
                    break;
                case 6:
                    System.out.print("Ingresa el ID del cliente: ");
                    String Clienteid = scanner.next(); 
                    scanner.nextLine(); // Limpiar el buffer
                    System.out.print("Ingresa el código del libro para la venta: ");
                    String codigoLibroVenta = scanner.nextLine();
    
                    System.out.print("Ingresa la cantidad de libros a vender: ");
                    int cantidad = scanner.nextInt(); // Obtener la cantidad a vender

                    biblioteca.registrarVenta("Venta-" + System.currentTimeMillis(), Clienteid, codigoLibroVenta, cantidad); // Usar un ID único para la venta
                    biblioteca.guardarVentasEnArchivo("Ventas.csv");
                    break;
                    
                case 7:
                    biblioteca.cargarVentasDesdeArchivo("Ventas.csv");
                    biblioteca.generarReporteVentas("Reporte.csv");
                    break;
                    
                case 8:
                    System.out.println("Saliendo del programa...");
                    break;
                    
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 8);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.entrega_final;

/**
 *
 * @author javie
 */
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class Entrega_Final {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);

        try {
            biblioteca.cargarDatos();
            System.out.println("Datos cargados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar los datos: " + e.getMessage());
            return; // Salir si no se pueden cargar los datos
        }

        // Hook para guardar datos al salir
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                biblioteca.guardarDatos();
                System.out.println("Datos guardados correctamente al salir.");
            } catch (IOException e) {
                System.out.println("Error al guardar los datos: " + e.getMessage());
            }
        }));

        String correo;
        String contraseña;
        Cliente clienteAutenticado = null;
        Empleado empleadoAutenticado = null;
        int rol = -1;
        // Bucle para la autenticación
        while (clienteAutenticado == null && empleadoAutenticado == null){
            System.out.print("Ingrese su correo (o 'SALIR' para salir): ");
            correo = scanner.nextLine();
            if (correo.equalsIgnoreCase("SALIR")) {
                System.out.println("Saliendo del programa...");
                scanner.close();
                return; // Salir si el usuario desea salir
            }
            System.out.print("Ingrese su contraseña (o 'SALIR' para salir): ");
            contraseña = scanner.nextLine();
            if (contraseña.equalsIgnoreCase("SALIR")) {
                System.out.println("Saliendo del programa...");
                scanner.close();
                return; // Salir si el usuario desea salir
            }

            // Validación de credenciales
            clienteAutenticado = biblioteca.validarCliente(correo, contraseña);
            empleadoAutenticado = biblioteca.validarEmpleado(correo, contraseña);
            if (clienteAutenticado == null && empleadoAutenticado == null) {
                System.out.println("Credenciales inválidas. Intente nuevamente.");
            }
            else if (empleadoAutenticado != null){
                rol = 0;
            }
            else{
                rol = 1;
            }
        }
        
        int opcion;
        Set<String> idVentas = new HashSet<>();
        do {
            System.out.println("\n--- Menú de Biblioteca ---");
            System.out.println("1. Mostrar contenido");
            System.out.println("2. Inserción libro");
            System.out.println("3. Modificar libro");
            System.out.println("4. Eliminar libro");
            System.out.println("5. Registrar cliente");
            System.out.println("6. Registrar venta");
            System.out.println("7. Reporte");
            System.out.println("8. Salir");
            System.out.print("Selecciona una opción: ");
            
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, ingresa un número válido.");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    biblioteca.mostrarContenido();
                    break;

                case 2:
                    if(rol == 0){
                        biblioteca.agregarLibro();
                    }
                    else{
                        System.out.println("ROL NO VÁLIDO PARA ESTA FUNCIÓN");
                    }
                    break;

                case 3:
                    if(rol == 0){
                        System.out.print("Ingresa el código del libro a modificar: ");
                        String codigoLibroModificar = scanner.nextLine();
                        biblioteca.modificarLibro(codigoLibroModificar);
                        biblioteca.cargarLibrosDesdeArchivo("Libros.csv");
                    }
                    else{
                        System.out.println("ROL NO VÁLIDO PARA ESTA FUNCIÓN");
                    }
                    break;

                case 4:
                    if(rol == 0){
                        System.out.print("Ingresa el código del libro a eliminar: ");
                        String codigoLibroEliminar = scanner.nextLine();
                        biblioteca.eliminarLibro(codigoLibroEliminar);
                        biblioteca.cargarLibrosDesdeArchivo("Libros.csv");
                    }
                    else{
                        System.out.println("ROL NO VÁLIDO PARA ESTA FUNCIÓN");
                    }
                    break;

                case 5:
                    if(rol == 0){
                        System.out.print("Ingresa el nombre del cliente: ");
                        String nombreCliente = scanner.nextLine();
                        System.out.print("Ingresa el ID del cliente: ");
                        String idCliente = scanner.next();
                        System.out.print("Ingresa el correo dxel cliente: ");
                        String correoCliente = scanner.nextLine();
                        System.out.print("Ingresa la contraseña del cliente: ");
                        String contraseñaCliente = scanner.next();
                        scanner.nextLine(); // Limpiar el buffer
                        biblioteca.registrarCliente(new Cliente(idCliente, nombreCliente,correoCliente, contraseñaCliente));
                        biblioteca.cargarClientesDesdeArchivo("Clientes.csv");
                    }
                    else{
                        System.out.println("ROL NO VÁLIDO PARA ESTA FUNCIÓN");
                    }
                    break;

                case 6:
                    System.out.print("Ingresa el ID del cliente: ");
                    String clienteId = scanner.next();
                    scanner.nextLine(); // Limpiar el buffer
                    System.out.print("Ingresa el código del libro para la venta: ");
                    String codigoLibroVenta = scanner.nextLine();
                    System.out.print("Ingresa la cantidad de libros a vender: ");
                    
                    int cantidad;
                    while (!scanner.hasNextInt() || (cantidad = scanner.nextInt()) <= 0) {
                        System.out.println("Ingresa una cantidad válida (número positivo).");
                        scanner.next();
                    }
                    
                    String idVenta = "Venta-" + System.currentTimeMillis();

                    if (!idVentas.contains(idVenta)) {
                        idVentas.add(idVenta);
                        biblioteca.registrarVenta(idVenta, clienteId, codigoLibroVenta, cantidad);
                        biblioteca.guardarVentasEnArchivo("Ventas.csv");
                    }
                    break;

                case 7:
                    if(rol == 0){
                        try {
                            biblioteca.cargarDatos();
                            biblioteca.generarReporteVentas("Reporte.txt");
                            biblioteca.guardarDatos();
                            System.out.println("Reporte generado y datos guardados correctamente.");
                        } catch (IOException e) {
                            System.out.println("Error al generar el reporte o guardar los datos: " + e.getMessage());
                        }
                    }
                    else{
                        System.out.println("ROL NO VÁLIDO PARA ESTA FUNCIÓN");
                    }
                    break;
                    
                case 8:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 8);

        scanner.close();
    }
}
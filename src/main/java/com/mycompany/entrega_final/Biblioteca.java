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
import java.io.IOException;

public class Biblioteca {
    private Map<Integer, Estante> estantes;
    private Map<String, Cliente> clientes;
    private Map<String, Empleado> empleados; 
    private Map<String, Venta> ventas; 
    
    public Biblioteca() {
        this.estantes = new HashMap<>();
        this.clientes = new HashMap<>();
        this.empleados = new HashMap<>();
        this.ventas = new HashMap<>();
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

    public void modificarLibro(String codigoLibro) {
        for (Estante estante : estantes.values()) {
            for (Libro libro : estante.getLibros()) {
                if (libro.getCodigo().equals(codigoLibro)) {
                    Scanner scanner = new Scanner(System.in);
                    
                    System.out.println("Modificar libro: " + libro.getTitulo());
                    System.out.print("Nuevo título (dejar vacío para no cambiar): ");
                    String nuevoTitulo = scanner.nextLine();
                    if (!nuevoTitulo.isEmpty()) {
                        libro.setTitulo(nuevoTitulo);
                    }
                    
                    System.out.print("Nuevo autor (dejar vacío para no cambiar): ");
                    String nuevoAutor = scanner.nextLine();
                    if (!nuevoAutor.isEmpty()) {
                        libro.setAutor(nuevoAutor);
                    }

                    System.out.print("Nuevo género (dejar vacío para no cambiar): ");
                    String nuevoGenero = scanner.nextLine();
                    if (!nuevoGenero.isEmpty()) {
                        libro.setGenero(nuevoGenero);
                    }

                    System.out.print("Nuevo puntaje (dejar vacío para no cambiar): ");
                    String puntajeInput = scanner.nextLine().replace(',', '.');

                    if (!puntajeInput.isEmpty()) {
                        try {
                            Double nuevoPuntaje = Double.parseDouble(puntajeInput);
                            libro.setPuntaje(nuevoPuntaje);
                        } catch (NumberFormatException e) {
                            System.out.println("Puntaje no válido. No se ha modificado.");
                        }
                    }

                    System.out.print("Nueva cantidad (dejar vacío para no cambiar): ");
                    String cantidadInput = scanner.nextLine();
                    if (!cantidadInput.isEmpty()) {
                        try {
                            Integer nuevaCantidad = Integer.parseInt(cantidadInput);
                            libro.setCantidad(nuevaCantidad);
                        } catch (NumberFormatException e) {
                            System.out.println("Cantidad no válida. No se ha modificado.");
                        }
                    }
                    
                    System.out.println("Libro modificado: " + libro.getCodigo());
                    return; // Salir de la función después de modificar
                }
            }
        System.out.println("Libro con código " + codigoLibro + " no encontrado.");
        }
    }

    public void eliminarLibro(String codigoLibro) {
        for (Estante estante : estantes.values()) {
            List<Libro> libros = estante.getLibros();
            Iterator<Libro> iterator = libros.iterator();
            while (iterator.hasNext()) {
                Libro libro = iterator.next();
                if (libro.getCodigo().equals(codigoLibro)) {
                    iterator.remove(); // Eliminar el libro de la lista
                    System.out.println("Libro con código " + codigoLibro + " eliminado.");
                    return; // Salir de la función después de eliminar
                }
            }
        }
        System.out.println("Libro con código " + codigoLibro + " no encontrado.");
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
                Libro libro = new Libro(datos[0], datos[1], datos[2], datos[3], Double.parseDouble(datos[4].replace(',', '.')), Integer.parseInt(datos[5]));

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
                    //System.out.println("Estante creado: " + estante.getNombre());
                }
        
                // Agregar el libro al estante correspondiente
                estante.agregarLibro(libro);
                //System.out.println("Libro agregado: " + libro.getTitulo() + " al estante " + estante.getNombre());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Método para guardar los cambios en el archivo CSV
    public void guardarLibrosEnArchivo(String rutaArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            bw.write("CÓDIGO,TÍTULO,AUTOR,GÉNEROS,PUNTAJE,CANTIDAD,CÓDIGO_ESTANTE,NOMBRE_ESTANTE\n");
            for (Estante estante : estantes.values()) {
                for (Libro libro : estante.getLibros()) {
                    // Guarda los libros en el archivo CSV
                    bw.write(libro.datos() + "," + estante.getCodigo() + "," + estante.getNombre() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void cargarClientesDesdeArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            // Leer la primera línea para omitir los encabezados
            if ((linea = br.readLine()) != null) {
                // Procesar encabezados si es necesario
            }

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                String id = datos[0];
                String nombre = datos[1];
                String correo = datos[2];
                String contraseña = datos[3];
                clientes.put(id, new Cliente(id, nombre, correo, contraseña));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void guardarClientesEnArchivo(String rutaArchivo) {
        try {
            // Usar append para no sobrescribir el archivo
            BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, false)); 
            // Escribir el encabezado solo si el archivo está vacío
            if (new File(rutaArchivo).length() == 0) {
                bw.write("ID_CLIENTE,NOMBRE,CORREO,CONTRASEÑA\n"); // Escribir encabezados
            }
        
            // Escribir cada cliente en el archivo
            for (Cliente cliente : clientes.values()) {
                bw.write(cliente.getId() + "," + cliente.getNombre() + "," + cliente.getCorreo() + "," + cliente.getContraseña() + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
     public void cargarEmpleadosDesdeArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            // Leer la primera línea para omitir los encabezados
            if ((linea = br.readLine()) != null) {
                // Procesar encabezados si es necesario
            }

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                String id = datos[0];
                String nombre = datos[1];
                String correo = datos[2];
                String contraseña = datos[3];
                empleados.put(id, new Empleado(id, nombre, correo, contraseña));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void guardarEmpleadosEnArchivo(String rutaArchivo) {
        try {
            // Usar append para no sobrescribir el archivo
            BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, false)); 
            // Escribir el encabezado solo si el archivo está vacío
            if (new File(rutaArchivo).length() == 0) {
                bw.write("ID_CLIENTE,NOMBRE,CORREO,CONTRASEÑA\n"); // Escribir encabezados
            }
        
            // Escribir cada cliente en el archivo
            for (Empleado empleado : empleados.values()) {
                bw.write(empleado.getId() + "," + empleado.getNombre() + "," + empleado.getCorreo() + "," + empleado.getContraseña() + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void cargarVentasDesdeArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            br.readLine(); // Omitir encabezados
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                String idVenta = datos[0];
                String idCliente = datos[1];
                String nombreCliente = datos[2];
                String tituloLibro = datos[3];
                int cantidad = Integer.parseInt(datos[4]);

                // Buscar el cliente en el mapa de clientes
                Cliente cliente = clientes.get(idCliente);
                if (cliente == null) {
                    System.out.println("Cliente con ID " + idCliente + " no encontrado. Saltando venta.");
                    continue; // Si no encuentra el cliente, salta esta venta
                }

                // Buscar el libro por título
                Libro libro = buscarLibroPorTitulo(tituloLibro);
                if (libro == null) {
                    System.out.println("Libro con título " + tituloLibro + " no encontrado. Saltando venta.");
                    continue; // Si no encuentra el libro, salta esta venta
                }

                // Crear la venta y agregarla al mapa
                Venta venta = new Venta(idVenta, cliente, libro, cantidad);
                ventas.put(idVenta, venta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardarVentasEnArchivo(String rutaArchivo) {
        try {
            // Usar append para no sobrescribir el archivo
            BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, false));
            // Escribir el encabezado solo si el archivo está vacío
            if (new File(rutaArchivo).length() == 0) {
                bw.write("ID_VENTA,ID_CLIENTE,NOMBRE_CLIENTE,TITULO_LIBRO,CANTIDAD\n"); // Escribir encabezados
            }

            // Escribir cada venta en el archivo
            for (Venta venta : ventas.values()) {
                bw.write(venta.getId() + "," + 
                venta.getCliente().getId() + "," +
                venta.getCliente().getNombre() + "," +
                venta.getLibro().getTitulo() + "," +
                venta.getCantidad() + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registrarCliente(Cliente cliente) {
        clientes.putIfAbsent(cliente.getId(), cliente);
    }

    public void registrarVenta(String idVenta, String idCliente, String codigoLibro, int cantidad) {
        Cliente cliente = clientes.get(idCliente);
    
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        Libro libro = null; // Inicializa la variable libro
        for (Estante estante : estantes.values()) { // Busca el libro en los estantes
            libro = estante.buscarLibroPorCodigo(codigoLibro); // Asumiendo que hay un método para buscar por código
            if (libro != null) {
                break; // Sale del bucle si el libro ha sido encontrado
            }
        }

        if (libro == null || libro.getCantidad() < cantidad) {
            System.out.println("Libro no disponible o cantidad insuficiente.");
            return;
        }

        Venta venta = new Venta(idVenta, cliente, libro, cantidad);
        ventas.put(idVenta, venta); // Agregado al mapa
        libro.setCantidad(libro.getCantidad() - cantidad); // Reduce la cantidad correctamente
        guardarLibrosEnArchivo("libros.csv");
        guardarVentasEnArchivo("ventas.csv");
        System.out.println("Venta registrada: " + idVenta);
    }


    public Libro buscarLibroPorTitulo(String titulo) {
        for (Estante estante : estantes.values()) {
            for (Libro libro : estante.getLibros()) {
                if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                    return libro;
                }
            }
        }
        return null; // Devuelve null si no se encuentra el libro
    }

    public Libro buscarLibroPorCodigo(String codigo) {
        for (Estante estante : estantes.values()) {
            Libro libro = estante.buscarLibroPorCodigo(codigo);
            if (libro != null) {
                return libro;
            }
        }
        return null; // Devuelve null si no se encuentra el libro
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

        // Crear el nuevo libro
        Libro nuevoLibro = new Libro(codigo, titulo, autor, genero, puntaje, cantidad);

        // Mostrar estantes disponibles
        System.out.println("Estantes disponibles:");
        for (Estante estante : estantes.values()) {
            System.out.println("Código: " + estante.getCodigo() + ", Nombre: " + estante.getNombre());
        }

        // Solicitar el código del estante al que se añadirá el libro
        System.out.print("Ingrese el código del estante al que desea agregar el libro: ");
        int codigoEstante = Integer.parseInt(scanner.nextLine());

        // Agregar el libro al estante seleccionado
        agregarLibroAEstante(codigoEstante, nuevoLibro);

        // Escribir en el archivo CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Libros.csv", true))) {
            bw.newLine();
            bw.write(nuevoLibro.datos() + "," + codigoEstante + "," + estantes.get(codigoEstante).getNombre());
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir el archivo: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Libro agregado y guardado exitosamente.");
    }  
    
    public void generarReporteVentas(String nombreArchivo) {
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas para generar un reporte.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            // Escribir encabezados
            writer.write("REPORTE DE VENTAS");
            writer.newLine();
            writer.write("------------------------------------------------");
            writer.newLine();

            // Encabezados para el reporte de ventas
            writer.write("ID_VENTA,ID_CLIENTE,NOMBRE_CLIENTE,CÓDIGO_LIBRO,TÍTULO_LIBRO,CANTIDAD,PUNTAJE");
            writer.newLine();

            for (Venta venta : ventas.values()) {
                Cliente cliente = venta.getCliente(); // Obtener el cliente de la venta
                Libro libro = venta.getLibro(); // Obtener el libro de la venta

                if (cliente == null || libro == null) {
                    System.out.println("Error: Cliente o libro no encontrado para la venta con ID: " + venta.getId());
                    continue; // Saltar esta venta si hay datos faltantes
                }

                // Crear la línea del reporte
                String linea = venta.getId() + "," +
                               cliente.getId() + "," +
                               cliente.getNombre() + "," +
                               libro.getCodigo() + "," +
                               libro.getTitulo() + "," +
                               venta.getCantidad() + "," +
                               libro.getPuntaje(); // Agregar puntaje del libro

                writer.write(linea);
                writer.newLine();
            }

            System.out.println("Reporte de ventas generado en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }    
    
    public void cargarDatos() throws IOException {
        cargarClientesDesdeArchivo("Clientes.csv");
        cargarLibrosDesdeArchivo("Libros.csv");
        cargarVentasDesdeArchivo("Ventas.csv");
        cargarEmpleadosDesdeArchivo("Empleados.csv"); 
    }

    public void guardarDatos() throws IOException {
        guardarClientesEnArchivo("Clientes.csv");
        guardarLibrosEnArchivo("Libros.csv");
        guardarVentasEnArchivo("Ventas.csv");
        guardarEmpleadosEnArchivo("Empleados.csv");
    }

    public Cliente validarCliente(String correo, String contraseña) {
        for (Cliente cliente : clientes.values()) {
            if (cliente.getCorreo().equals(correo) && cliente.getContraseña().equals(contraseña)) {
                return cliente; // Retorna el cliente autenticado
            }
        }
        return null; // Retorna null si las credenciales no son válidas
    }

    public Empleado validarEmpleado(String correo, String contraseña) {
        for (Empleado empleado : empleados.values()) {
            if (empleado.getCorreo().equals(correo) && empleado.getContraseña().equals(contraseña)) {
                return empleado; // Retorna el cliente autenticado
            }
        }
        return null; // Retorna null si las credenciales no son válidas
    }
    
}

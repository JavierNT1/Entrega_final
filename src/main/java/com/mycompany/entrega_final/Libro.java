/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entrega_final;

/**
 *
 * @author javie
 */

public class Libro {
    private String codigo;
    private String titulo;
    private String autor;
    private String genero; 
    private double puntaje;
    private int cantidad;

    // Constructor por defecto
    public Libro() {
        this.codigo = "";
        this.titulo = "";
        this.autor = "";
        this.genero = ""; 
        this.puntaje = 0.0;
        this.cantidad = 0;
    }

    // Constructor con parámetros
    public Libro(String codigo, String titulo, String autor, String genero, double puntaje, int cantidad) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero; 
        this.puntaje = puntaje;
        this.cantidad = cantidad;
    }

    // Métodos getters y setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public void actualizar(double nuevoPuntaje) {
        this.puntaje = nuevoPuntaje;
    }

    public void actualizar(int nuevaCantidad) {
        this.cantidad = nuevaCantidad;
    }
        
    // Método para mostrar la información del libro
    public void mostrarInfo() {
        System.out.println("Código: " + codigo);
        System.out.println("Nombre: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Género: " + genero); // Mostrar el género del libro
        System.out.println("Puntaje: " + puntaje);
        System.out.println("Cantidad: " + cantidad);
    }

    public String datos() {
        return String.format("%s,%s,%s,%s,%s,%d", codigo, titulo, autor, genero, String.format("%.1f", puntaje).replace(',', '.'), cantidad);
    }
}


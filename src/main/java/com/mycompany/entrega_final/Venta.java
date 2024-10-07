/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entrega_final;

/**
 *
 * @author javie
 */

public class Venta {
    private String id;
    private Cliente cliente;
    private Libro libro;
    private int cantidad;

    public Venta(String id, Cliente cliente, Libro libro, int cantidad) {
        this.id = id;
        this.cliente = cliente;
        this.libro = libro;
        this.cantidad = cantidad;
    }

    // Métodos getters
    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Libro getLibro() {
        return libro;
    }

    public int getCantidad() {
        return cantidad;
    }
}


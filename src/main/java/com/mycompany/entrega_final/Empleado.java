/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entrega_final;

/**
 *
 * @author javie
 */
public class Empleado extends Persona {
    public Empleado(String id, String nombre, String correo, String contraseña) {
        super(id, nombre, correo, contraseña);
    }
    
    @Override
    public void mostrarDetalles() {
        System.out.println("Detalles del Empleado:");
        super.mostrarDetalles();
    }
    
}



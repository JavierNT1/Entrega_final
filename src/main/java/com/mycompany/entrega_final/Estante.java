/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entrega_final;

/**
 *
 * @author javie
 */
import java.util.ArrayList;
import java.util.List;

public class Estante {
    private int codigo;
    private String nombre;
    private List<Libro> libros;

    public Estante(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.libros = new ArrayList<>();
    }

    // Getter para el atributo codigo
    public int getCodigo() {
        return codigo;
    }
  
    // Getter para el atributo nombre
    public String getNombre() {
        return nombre;
    }
  
    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public List<Libro> getLibros() {
        return libros;
    }

    // Método para buscar un libro por su código
    public Libro buscarLibroPorCodigo(String codigo) {
        for (Libro libro : libros) {
            if (libro.getCodigo().equals(codigo)) { // Asegúrate de que getCodigo() devuelva un String
                return libro;
            }
        }
        return null; // Devuelve null si no se encuentra el libro
    }
    
    @Override
    public String toString() {
        return "Estante{" + "codigo=" + codigo + ", nombre=" + nombre + ", libros=" + libros + '}';
    }
}

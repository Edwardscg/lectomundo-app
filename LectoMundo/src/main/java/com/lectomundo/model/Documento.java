package com.lectomundo.model;

import java.time.LocalDate;

public class Documento {
    private int id_documento;
    private String titulo;
    private String autor;
    private String tipo_documento;
    private LocalDate fecha_publicacion;
    private String genero;
    private String descripcion;
    private String pdf_url;
    private String portada_url;
    private int precio;
    private float puntuacion_promedio;
    private int cantidad_valoraciones;

    public Documento(int id_documento, String titulo, String autor, String tipo_documento, LocalDate fecha_publicacion, String genero, String descripcion, String pdf_url, String portada_url, int precio, float puntuacion_promedio, int cantidad_valoraciones) {
        this.id_documento = id_documento;
        this.titulo = titulo;
        this.autor = autor;
        this.tipo_documento = tipo_documento;
        this.fecha_publicacion = fecha_publicacion;
        this.genero = genero;
        this.descripcion = descripcion;
        this.pdf_url = pdf_url;
        this.portada_url = portada_url;
        this.precio = precio;
        this.puntuacion_promedio = puntuacion_promedio;
        this.cantidad_valoraciones = cantidad_valoraciones;
    }

    public int getId_documento() {
        return id_documento;
    }

    public void setId_documento(int id_documento) {
        this.id_documento = id_documento;
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

    public String getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    public LocalDate getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(LocalDate fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPdf_url() {
        return pdf_url;
    }

    public void setPdf_url(String pdf_url) {
        this.pdf_url = pdf_url;
    }

    public String getPortada_url() {
        return portada_url;
    }

    public void setPortada_url(String portada_url) {
        this.portada_url = portada_url;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public float getPuntuacion_promedio() {
        return puntuacion_promedio;
    }

    public void setPuntuacion_promedio(float puntuacion_promedio) {
        this.puntuacion_promedio = puntuacion_promedio;
    }

    public int getCantidad_valoraciones() {
        return cantidad_valoraciones;
    }

    public void setCantidad_valoraciones(int cantidad_valoraciones) {
        this.cantidad_valoraciones = cantidad_valoraciones;
    }
}

package com.vending.machine.model;

import com.vending.machine.helper.EstadoVenta;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String producto;
    private double precio;

    @Enumerated(EnumType.STRING)
    private EstadoVenta estado;

    private String codigoQR;
    private LocalDateTime fechaCreacion;

    public Venta() {
    }

    public Venta(Long id, String producto, double precio, EstadoVenta estado, String codigoQR, LocalDateTime fechaCreacion) {
        this.id = id;
        this.producto = producto;
        this.precio = precio;
        this.estado = estado;
        this.codigoQR = codigoQR;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public EstadoVenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}

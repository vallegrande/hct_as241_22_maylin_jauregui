package hctjaureguimaylin.hackathon.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "vehiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Vehiculo {


    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "placa", nullable = false)
    private String placa;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "anio", nullable = false)
    private String anio;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "precio", nullable = false)
    private Integer precio;

    @Column(name = "activo", nullable = false)
    private Boolean activo;
}

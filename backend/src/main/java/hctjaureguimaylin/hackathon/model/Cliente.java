package hctjaureguimaylin.hackathon.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cliente {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "dni", nullable = false)
    private String dni;

    @Column(name = "nombres", nullable = false)
    private String nombres;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @Column(name = "celular", nullable = false)
    private String celular;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "licencia", nullable = false)
    private String licencia;

    @Column(name = "estado", nullable = false)
    private String estado;
}

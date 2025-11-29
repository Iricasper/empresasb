package com.companyname.empresasb.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "nominas")
@Data
public class Nomina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10)
    private double sueldo;

    @Column(name = "fecha_calculo")
    private LocalDate fechaCalculo;

    @OneToOne
    @JoinColumn(name = "empleado_id", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_nomina_empleado"))
    private Empleado empleado;

    private static final int[] SUELDO_BASE = {
            50000, 70000, 90000, 110000, 130000,
            150000, 170000, 190000, 210000, 230000
    };

    public Nomina() {}

    @Transient
    public double sueldo(Empleado e) {
        return SUELDO_BASE[e.getCategoria() - 1] + (5000 * e.getAnyos());
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getSueldo() { return sueldo; }
    public void setSueldo(double sueldo) { this.sueldo = sueldo; }

    public LocalDate getFechaCalculo() { return fechaCalculo; }
    public void setFechaCalculo(LocalDate fechaCalculo) { this.fechaCalculo = fechaCalculo; }

    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
}

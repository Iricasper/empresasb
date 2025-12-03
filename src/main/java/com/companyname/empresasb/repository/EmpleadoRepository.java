package com.companyname.empresasb.repository;

import com.companyname.empresasb.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado,Long> {
    List<Empleado> findByDniContainsIgnoreCase(String dni);
    List<Empleado> findByDniIgnoreCase(String dni);
    List<Empleado> findByNombreContainsIgnoreCase(String nombre);
    List<Empleado> findBySexo(char sexo);
    List<Empleado> findByCategoria(int categoria);
    List<Empleado> findByAnyos(int anyos);
}

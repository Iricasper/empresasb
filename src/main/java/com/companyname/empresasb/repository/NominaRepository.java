package com.companyname.empresasb.repository;

import com.companyname.empresasb.model.Nomina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NominaRepository extends JpaRepository<Nomina,Integer> {
    Optional<Nomina> findByEmpleadoDni(String dni);
}

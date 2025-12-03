package com.companyname.empresasb.service;

import com.companyname.empresasb.model.Empleado;
import com.companyname.empresasb.model.Nomina;
import com.companyname.empresasb.repository.EmpleadoRepository;
import com.companyname.empresasb.repository.NominaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private NominaRepository nominaRepository;

    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }

    public List<Empleado> buscarEmpleadosPorCampo(String campo, String valor) {
        if (valor == null || valor.isEmpty()) return List.of();

        return switch (campo) {
            case "dni" -> empleadoRepository.findByDniContainsIgnoreCase(valor);
            case "nombre" -> empleadoRepository.findByNombreContainsIgnoreCase(valor);
            case "sexo" -> {
                char s = valor.toUpperCase().charAt(0);
                yield empleadoRepository.findBySexo(s);
            }
            case "categoria" -> {
                int cat = Integer.parseInt(valor);
                yield empleadoRepository.findByCategoria(cat);
            }
            case "anyos" -> {
                int any = Integer.parseInt(valor);
                yield empleadoRepository.findByAnyos(any);
            }
            default -> throw new IllegalArgumentException("Campo no válido: " + campo);
        };
    }

    @Transactional
    public boolean editarEmpleado(Empleado empleado) {
        List<Empleado> empleadoEdit = empleadoRepository.findByDniContainsIgnoreCase(empleado.getDni());
        if (empleadoEdit.isEmpty()) return false;

        Empleado e = empleadoEdit.get(0);
        e.setNombre(empleado.getNombre());
        e.setSexo(empleado.getSexo());
        e.setCategoria(empleado.getCategoria());
        e.setAnyos(empleado.getAnyos());
        empleadoRepository.save(e);

        Nomina nomina = nominaRepository.findByEmpleadoDni(e.getDni())
                .orElse(new Nomina());
        nomina.setEmpleado(e);
        nomina.setSueldo(nomina.sueldo(e));
        nomina.setFechaCalculo(LocalDate.now());
        nominaRepository.save(nomina);

        return true;
    }

    public Map<String, Object> obtenerNominaPorDni(String dni){
        List<Empleado> empleados = empleadoRepository.findByDniIgnoreCase(dni);
        if (empleados.isEmpty()) return null;

        Empleado e = empleados.getFirst();
        Optional<Nomina> nominaOptional = nominaRepository.findByEmpleadoDni(e.getDni());

        Map<String, Object> data = new HashMap<>();
        data.put("nombre", e.getNombre());
        data.put("dni", e.getDni());
        data.put("sueldo", nominaOptional.map(Nomina::getSueldo).orElse(0.0));
        data.put("sueldo_calculado", new Nomina().sueldo(e));
        return data;
    }

}

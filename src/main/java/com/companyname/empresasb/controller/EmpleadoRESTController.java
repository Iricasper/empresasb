package com.companyname.empresasb.controller;

import com.companyname.empresasb.model.Empleado;
import com.companyname.empresasb.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("rest/empleados")
public class EmpleadoRESTController {
    @Autowired
    private EmpleadoService empleadoService;

    // Listar todos los empleados
    // Get en Postman: http://localhost:8080/rest/empleados
    @GetMapping
    public ResponseEntity<List<Empleado>> listarEmpleados(){
        List<Empleado> listaEmpleados =  empleadoService.findAll();
        return ResponseEntity.ok(listaEmpleados);
    }

    // Buscar empleados por campos concretos
    // Get en Postman: http://localhost:8080/rest/empleados/buscar?campo=dni&valor=3
    @GetMapping("buscar")
    public ResponseEntity<List<Empleado>> buscarEmpleadosPorCampo(@RequestParam String campo, @RequestParam String valor) {
        if (campo.isBlank() || valor.isBlank())
            return ResponseEntity.badRequest().build();
        List<Empleado> empleadosBuscados = empleadoService.buscarEmpleadosPorCampo(campo, valor);
        return ResponseEntity.ok(empleadosBuscados);
    }

    // Buscar el salario de un empleado dado su dni
    // Get en Postman: http://localhost:8080/rest/empleados/nomina?dni=32000031R
    @GetMapping("nomina")
    public ResponseEntity<Map<String, Object>> obtenerNominaPorDni(@RequestParam String dni) {
        if (dni.length() != 9)
            return ResponseEntity.badRequest().build();
        Map<String, Object> empleadoBuscado = empleadoService.obtenerNominaPorDni(dni);
        if (empleadoBuscado == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(empleadoBuscado);
    }

    // Editar empleado
    // Post en Postman: http://localhost:8080/rest/empleados/editar/32000032G
    /* Raw Body de ejemplo:
        {
            "id": 1,
            "dni": "32000032G",
            "nombre": "James Cosling",
            "sexo": "F",
            "anyos": 7,
            "categoria": 9
        }
     */
    @PutMapping("editar/{dni}")
    public ResponseEntity<Object> editarEmpleado(@RequestBody Empleado empleado) {
        try {
            if (empleado.getDni().length() != 9 || empleado.getNombre().isBlank() || empleado.getSexo() == '\0')
                return ResponseEntity.badRequest().body("Los campos son obligatorios");
            empleadoService.editarEmpleado(empleado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Empleado editado con éxito");
    }
}

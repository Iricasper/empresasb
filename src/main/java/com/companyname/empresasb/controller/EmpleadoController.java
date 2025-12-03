package com.companyname.empresasb.controller;

import com.companyname.empresasb.model.Empleado;
import com.companyname.empresasb.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("empleados")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

    // Listar todos los empleados
    @GetMapping
    public String listarEmpleados(Model model) {
        List<Empleado> listaEmpleados = empleadoService.findAll();
        model.addAttribute("listaEmpleados", listaEmpleados);
        return "empleados";
    }

    // Buscar empleados por campos concretos
    @GetMapping("buscar")
    public String mostrarFormularioBuscarEmpleados() {
        return "buscarEmpleado";
    }

    @PostMapping("buscar")
    public String buscarEmpleadosPorCampo(
            Model model, @RequestParam String campo, @RequestParam String valor) {
        List<Empleado> empleadosBuscados = (empleadoService.buscarEmpleadosPorCampo(campo, valor) == null) ? List.of() : empleadoService.buscarEmpleadosPorCampo(campo, valor);
        model.addAttribute("empleadosBuscados", empleadosBuscados);
        return "resultadoBusqueda";
    }

    // Buscar el salario de un empleado dado su dni
    @GetMapping("nomina")
    public String mostrarFormularioSalario() {
        return "buscarSalario";
    }

    @PostMapping("nomina")
    public String buscarEmpleadoPorDni(
            Model model, @RequestParam String dni) {
        Map<String, Object> empleadoBuscado = (empleadoService.obtenerNominaPorDni(dni));
        model.addAttribute("formularioEnviado", true);
        model.addAllAttributes(empleadoBuscado);
        return "buscarSalario";
    }


}

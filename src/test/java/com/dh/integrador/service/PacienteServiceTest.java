package com.dh.integrador.service;

import com.dh.integrador.entity.Domicilio;
import com.dh.integrador.entity.Paciente;
import com.dh.integrador.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class PacienteServiceTest {
    @Autowired
    private PacienteService pacienteService;

    @Test
    @Order(1)
    public void guardarPacienteTest(){
        Paciente pacienteXGuardar= new Paciente("Jhonatan",
                "Escobar","123454", LocalDate.of(2023,11,18),
                "jhont@gmail.com",new Domicilio("Calle B",26,"Medellín",
                "Antioquia"));
        Paciente pacienteGuardado=pacienteService.guardarPaciente(pacienteXGuardar);
        assertEquals(1L,pacienteGuardado.getId());
    }
    @Test
    @Order(2)
    public void buscarPacientePorIDTest(){
        Long idABuscar=1L;
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPaciente(idABuscar);
        assertNotNull(pacienteBuscado.get());
    }
    @Test
    @Order(3)
    public void buscarPacientesTest(){
        List<Paciente> pacientes=pacienteService.buscarPacientes();
        assertEquals(1,pacientes.size());
    }
    @Test
    @Order(4)
    public void actualizarPacienteTest(){
        Paciente pacienteXActualizar= new Paciente(1L,"Gojan",
                "Tobon","5674", LocalDate.of(2022,12,28),
                "tobon@gmail.com",new Domicilio("Cll A",67,"Medellín",
                "Antioquia"));
        pacienteService.actualizarPaciente(pacienteXActualizar);
        Optional<Paciente> pacienteActualizado=pacienteService.buscarPaciente(1L);
        assertEquals("Gojan",pacienteActualizado.get().getNombre());
    }
    @Test
    @Order(5)
    public void eliminarPacienteTest() throws ResourceNotFoundException {
        Long idAEliminar=1L;
        pacienteService.eliminarPaciente(idAEliminar);
        Optional<Paciente> pacienteEliminado=pacienteService.buscarPaciente(idAEliminar);
        assertFalse(pacienteEliminado.isPresent());
    }
}
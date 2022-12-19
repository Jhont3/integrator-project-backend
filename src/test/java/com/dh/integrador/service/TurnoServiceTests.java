package com.dh.integrador.service;

import com.dh.integrador.dto.TurnoDTO;
import com.dh.integrador.entity.Domicilio;
import com.dh.integrador.entity.Odontologo;
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
public class TurnoServiceTests {
    @Autowired
    TurnoService turnoService;
    @Autowired
    PacienteService pacienteService;
    @Autowired
    OdontologoService odontologoService;

    @Test
    @Order(1)
    public void guardarTurnoTest(){
        Paciente pacienteAGuardar= new Paciente("Jhonatan",
                "Escobar","123454", LocalDate.of(2023,11,18),
                "jhont@gmail.com",new Domicilio("Calle B",26,"Medellín",
                "Antioquia"));
        pacienteService.guardarPaciente(pacienteAGuardar);
        Odontologo odontologoXRegistrar= new Odontologo("NP3982","Lucas","Cardenas");
        odontologoService.guardarOdontologo(odontologoXRegistrar);
        TurnoDTO turnoDto= new TurnoDTO();
        turnoDto.setFecha(LocalDate.of(2024,9,27));
        turnoDto.setPacienteId(pacienteAGuardar.getId());
        turnoDto.setOdontologoId(odontologoXRegistrar.getId());
        TurnoDTO turnoDtoGuardado = turnoService.guardarTurno(turnoDto);
        assertEquals(1L,turnoDtoGuardado.getId());
    }
    @Test
    @Order(2)
    public void buscarTurnoPorIDTest(){
        Long idABuscar=1L;
        Optional<TurnoDTO> turnoBuscado=turnoService.buscarTurno(idABuscar);
        assertNotNull(turnoBuscado.get());
    }

    @Test
    @Order(3)
    public void listarTurnosTest(){
        List<TurnoDTO> turnos=turnoService.listarTurnos();
        assertEquals(1,turnos.size());
    }
    @Test
    @Order(4)
    public void actualizarTurnoTest(){
        TurnoDTO turnoDto= new TurnoDTO();
        turnoDto.setFecha(LocalDate.of(2022,12,12));
        turnoDto.setId(1L);
        turnoDto.setOdontologoId(1L);
        turnoDto.setPacienteId(1L);
        turnoService.actualizarTurno(turnoDto);
        assertEquals(1L,turnoDto.getId());
    }

    @Test
    @Order(5)
    public void eliminarTurnoTest() throws ResourceNotFoundException {
        Long idPorEliminar = 1L;
        turnoService.eliminarTurno(idPorEliminar);
        Optional<TurnoDTO> turnoEliminado=turnoService.buscarTurno(idPorEliminar);
        assertFalse(turnoEliminado.isPresent());
    }
}

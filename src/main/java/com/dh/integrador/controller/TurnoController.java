package com.dh.integrador.controller;

import com.dh.integrador.dto.TurnoDTO;
import com.dh.integrador.entity.Odontologo;
import com.dh.integrador.entity.Paciente;
import com.dh.integrador.exceptions.BadRequestException;
import com.dh.integrador.exceptions.ResourceNotFoundException;
import com.dh.integrador.service.OdontologoService;
import com.dh.integrador.service.PacienteService;
import com.dh.integrador.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private TurnoService turnoService;
    private PacienteService pacienteService;
    private OdontologoService odontologoService;

    @Autowired
    public TurnoController(TurnoService turnoService, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoService = turnoService;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }
    @GetMapping
    public ResponseEntity<List<TurnoDTO>> buscarTurnos(){
        return ResponseEntity.ok(turnoService.listarTurnos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> buscarTurno(@PathVariable Long id){
        Optional<TurnoDTO> turnoBuscado=turnoService.buscarTurno(id);
        if (turnoBuscado.isPresent()){
            return ResponseEntity.ok(turnoBuscado.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TurnoDTO> registrarTurno(@RequestBody TurnoDTO turno){
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPaciente(turno.getPacienteId());
        Optional<Odontologo> odontologoBuscado=odontologoService.buscarOdontologo(turno.getOdontologoId());

        if(odontologoBuscado.isPresent() && pacienteBuscado.isPresent()){
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("Se eliminó satisfactoriamente el turno con id= " + id);
    }

    @PutMapping()
    public ResponseEntity<String> actualizarTurno(@RequestBody TurnoDTO turno) throws BadRequestException {
        Optional<TurnoDTO> turnoBuscado=turnoService.buscarTurno(turno.getId());
        if (turnoBuscado.isPresent()){
            if(odontologoService.buscarOdontologo(turno.getOdontologoId()).isPresent()
                    &&pacienteService.buscarPaciente(turno.getPacienteId()).isPresent()){
                turnoService.actualizarTurno(turno);
                return ResponseEntity.ok("Se actualizó el turno con id= "+turno.getId());
            }
            else{
                throw new BadRequestException("No se logro registrar el turno, verificar id de paciente y odontólogo");
            }
        }
        else {
            return ResponseEntity.badRequest().body("No se encontró el turno con id suministrado");
        }
    }

}

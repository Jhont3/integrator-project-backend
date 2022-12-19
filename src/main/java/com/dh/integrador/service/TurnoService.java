package com.dh.integrador.service;

import com.dh.integrador.dto.TurnoDTO;
import com.dh.integrador.entity.Odontologo;
import com.dh.integrador.entity.Paciente;
import com.dh.integrador.entity.Turno;
import com.dh.integrador.exceptions.ResourceNotFoundException;
import com.dh.integrador.repository.TurnoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {

    private TurnoRepository turnoRepository;
    @Autowired
    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    private static final Logger LOGGER= Logger.getLogger(PacienteService.class);

    public List<TurnoDTO> listarTurnos(){
        List<Turno> turnosEncontrados=turnoRepository.findAll();
        List<TurnoDTO> respuesta= new ArrayList<>();
        LOGGER.info("Se inició proceso de buscar todos los turnos");
        for (Turno turno:turnosEncontrados) {
            respuesta.add(turnoATurnoDTO(turno));
            LOGGER.info("Se inició añadio turno satisfactoriamente");
        }
        return respuesta;
    }
    public Optional<TurnoDTO> buscarTurno(Long id){
        Optional<Turno> turnoBuscado=turnoRepository.findById(id);
        LOGGER.info("Se inició proceso de buscar un turno con id: "+id);
        if (turnoBuscado.isPresent()){
            LOGGER.info("Se encontro turno con id: "+id);
            return Optional.of(turnoATurnoDTO(turnoBuscado.get()));
        }
        else{
            LOGGER.info("No se encontró turno con id: "+id);
            return Optional.empty();
        }
    }
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        Optional<TurnoDTO> turnoAEliminar = buscarTurno(id);
        LOGGER.info("Se inició proceso de eliminar turno con id: "+id);
        if (turnoAEliminar.isPresent()) {
            LOGGER.info("Se borró turno con id: "+id);
            turnoRepository.deleteById(id);
        } else {
            LOGGER.info("No se encontró turno con id: "+id);
            throw new ResourceNotFoundException("No se pudo eliminar el turno con id= " +id+" ya que no fue encontrado");
        }
    }
    public void actualizarTurno(TurnoDTO turnodto){
        LOGGER.info("Se inició proceso de actualizar un turno con id: "+turnodto.getId());
        turnoRepository.save(turnoDTOATurno(turnodto));
    }
    public TurnoDTO guardarTurno(TurnoDTO turnodto){
        Turno turnoGuardado=turnoRepository.save(turnoDTOATurno(turnodto));
        LOGGER.info("Se inició proceso de guardar turno");
        return turnoATurnoDTO(turnoGuardado);
    }
    private TurnoDTO turnoATurnoDTO(Turno turno){
        TurnoDTO respuesta= new TurnoDTO();
        respuesta.setId(turno.getId());
        respuesta.setPacienteId(turno.getPaciente().getId());
        respuesta.setOdontologoId(turno.getOdontologo().getId());
        respuesta.setFecha(turno.getFecha());
        return respuesta;
    }
    private Turno turnoDTOATurno(TurnoDTO turnodto){
        Turno respuesta= new Turno();
        Odontologo odontologo= new Odontologo();
        Paciente paciente= new Paciente();
        odontologo.setId(turnodto.getOdontologoId());
        paciente.setId(turnodto.getPacienteId());
        respuesta.setFecha(turnodto.getFecha());
        respuesta.setId(turnodto.getId());
        respuesta.setOdontologo(odontologo);
        respuesta.setPaciente(paciente);
        return respuesta;
    }
}

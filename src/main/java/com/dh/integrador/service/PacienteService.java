package com.dh.integrador.service;

import com.dh.integrador.entity.Paciente;
import com.dh.integrador.exceptions.ResourceNotFoundException;
import com.dh.integrador.repository.PacienteRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    private PacienteRepository pacienteRepository;

    private static final Logger LOGGER= Logger.getLogger(PacienteService.class);
    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente guardarPaciente(Paciente paciente){
        LOGGER.info("Se inició proceso de guardado de paciente");
        return pacienteRepository.save(paciente);
    }
    public Optional<Paciente> buscarPaciente(Long id){
        LOGGER.info("Se inició proceso de buscar paciente con id: "+ id);
        return pacienteRepository.findById(id);
    }
    public void eliminarPaciente(Long id) throws ResourceNotFoundException {
        LOGGER.info("Se inició proceso de eliminar paciente con id: "+ id);
        Optional<Paciente> pacienteAEliminar = buscarPaciente(id);
        if(pacienteAEliminar.isPresent()) {
            LOGGER.info("Se eliminó paciente con id: "+ id);
            pacienteRepository.deleteById(id);
        }
        else {
            throw new ResourceNotFoundException("Error. No se pudo eliminar el paciente "+ id +" ya que no fue encontrado");
        }
    }
    public void actualizarPaciente(Paciente paciente){
        LOGGER.info("Se inició proceso de actualizar paciente con id: "+ paciente.getId());
        pacienteRepository.save(paciente);
    }
    public List<Paciente> buscarPacientes(){
        LOGGER.info("Se inició proceso de buscar todos los pacientes");
        return pacienteRepository.findAll();
    }
    public Optional<Paciente> buscarPacientePorCorreo(String correo){
        LOGGER.info("Se inició proceso de buscar paciente por correo: "+correo);
        return pacienteRepository.findByEmail(correo);
    }
}

package com.dh.integrador.service;
import com.dh.integrador.entity.Odontologo;
import com.dh.integrador.exceptions.ResourceNotFoundException;
import com.dh.integrador.repository.OdontologoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    private static final Logger LOGGER= Logger.getLogger(OdontologoService.class);
    private OdontologoRepository odontologoRepository;
    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo){
        LOGGER.info("Se inició proceso de guardado de odontólogo");
        return odontologoRepository.save(odontologo);
    }
    public Optional<Odontologo> buscarOdontologo(Long id){
        LOGGER.info("Se inició proceso de buscar odontólogo con id: "+ id);
        return odontologoRepository.findById(id);
    }
    public void actualizarOdontologo(Odontologo odontologo){
        LOGGER.info("Se inició proceso de actualizar odontólogo con id: "+ odontologo.getId());
        odontologoRepository.save(odontologo);
    }
    public void eliminarOdontologo(Long id) throws ResourceNotFoundException{
        Optional<Odontologo> odontologoAEliminar=buscarOdontologo(id);
        LOGGER.info("Se inició proceso de eliminar odontólogo con id: "+ id);
        if (odontologoAEliminar.isPresent()){
            odontologoRepository.deleteById(id);
            LOGGER.info("Se eliminó odontólogo con id: "+ id);
        }
        else {
            throw new ResourceNotFoundException("No se puede eliminar el odontólogo con id= " + id +
                    " ya que no existe un odontólogo en la base de datos con el id suministrado");
        }
    }
    public List<Odontologo> buscarTodosOdontologos(){
        LOGGER.info("Se inició proceso de buscar todos los odontólogos");
        return odontologoRepository.findAll();
    }

}

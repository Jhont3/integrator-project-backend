package com.dh.integrador.service;

import com.dh.integrador.entity.Odontologo;
import com.dh.integrador.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class OdontologoServiceTests {
    @Autowired
    private OdontologoService odontologoService;
    @Test
    @Order(1)
    public void guardarOdontologoTest(){
        Odontologo odontologoXRegistrar= new Odontologo("NP3982","Lucas","Cardenas");
        Odontologo odontologoGuardado=odontologoService.guardarOdontologo(odontologoXRegistrar);
        assertEquals(1L,odontologoGuardado.getId());
}
    @Test
    @Order(2)
    public void buscarOdontologoPorIDTest(){
        Long idABuscar=1L;
        Optional<Odontologo> odontologoBuscado=odontologoService.buscarOdontologo(idABuscar);
        assertNotNull(odontologoBuscado.get());
    }

    @Test
    @Order(3)
    public void buscarOdontologosTest(){
        List<Odontologo> odontologos=odontologoService.buscarTodosOdontologos();
        assertEquals(1,odontologos.size());
    }
    @Test
    @Order(4)
    public void actualizarOdontologoTest(){
        Odontologo odontologoXActualizar= new Odontologo(1L,"NP3982","Lucas","Cardona");
        odontologoService.actualizarOdontologo(odontologoXActualizar);
        Optional<Odontologo> odontologoActualizado=odontologoService.buscarOdontologo(1L);
        assertEquals("Lucas",odontologoActualizado.get().getNombre());
    }
    @Test
    @Order(5)
    public void eliminarOdontologoTest() throws ResourceNotFoundException {
        Long idPorEliminar = 1L;
        odontologoService.eliminarOdontologo(idPorEliminar);
        Optional<Odontologo> odontologoEliminado=odontologoService.buscarOdontologo(idPorEliminar);
        assertFalse(odontologoEliminado.isPresent());
    }
}

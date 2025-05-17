package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.repository;

import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.Carrito;
import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.CarritoItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarritoItemRepository extends CrudRepository<CarritoItem, Long> {
    List<CarritoItem> findByCarrito(Carrito carrito);
}

package edu.comillas.icai.gitt.pat.spring.ProyectoFinal.model;

import edu.comillas.icai.gitt.pat.spring.ProyectoFinal.entidad.CarritoItem;

public record CarritoItemInterm(
            Long id,
            Long productoApiId,
            String nombre,
            String imagenUrl,
            double precio,
            int cantidad
    ) {
        public static CarritoItemInterm fromEntity(CarritoItem item) {
            return new CarritoItemInterm(
                    item.getId(),
                    item.getProductoApiId(),
                    item.getNombre(),
                    item.getImagenUrl(),
                    item.getPrecio(),
                    item.getCantidad()
            );
        }
}



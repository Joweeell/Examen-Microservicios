package com.example.Micro_Productos.service;

import com.example.Micro_Productos.model.Producto;
import com.example.Micro_Productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> obtenerTodos(){
        return productoRepository.findAll();
    }

    public Producto guardar(Producto producto) {
        if (producto.getPrecio() < 0) {
            throw new RuntimeException("El precio no puede ser negativo");
        }
        return productoRepository.save(producto);
    }

    public List<Producto> buscarPorCategoria(String categoria){
            return productoRepository.findByCategoria(categoria);}

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)){
                throw new RuntimeException("El producto con ID: " + "no existe.");
            }
            productoRepository.deleteById(id);
    }
    public Producto actualizar(Long id, Producto ProductoNuevo){
        Optional<Producto> productoData = productoRepository.findById(id);
        if (productoData.isPresent()){
            Producto productoExistente = productoData.get();

            productoExistente.setNombre(ProductoNuevo.getNombre());
            productoExistente.setDescripcion(ProductoNuevo.getDescripcion());
            productoExistente.setPrecio(ProductoNuevo.getPrecio());
            productoExistente.setStock(ProductoNuevo.getStock());
            productoExistente.setCategoria(ProductoNuevo.getCategoria());

            return productoRepository.save(productoExistente);
        }
        return null;
    }


}

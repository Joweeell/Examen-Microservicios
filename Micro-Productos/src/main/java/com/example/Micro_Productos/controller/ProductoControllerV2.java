package com.example.Micro_Productos.controller;

import com.example.Micro_Productos.assemblers.ProductoModelAssembler;
import com.example.Micro_Productos.model.Producto;
import com.example.Micro_Productos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/productos") // Versión 2
@Tag(name = "Productos V2", description = "Operaciones de productos bajo formato hipermedia HATEOAS")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener catálogo completo (HATEOAS)", description = "Devuelve la colección de productos con formato hipermedia")
    public CollectionModel<EntityModel<Producto>> getAllProductos() {
        List<Producto> listaPlana = productoService.obtenerTodos();

        List<EntityModel<Producto>> productos = listaPlana.stream()
                .map(producto -> assembler.toModel(producto))
                .collect(Collectors.toList());

        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoControllerV2.class).getAllProductos()).withSelfRel());
    }


    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener producto por ID (HATEOAS)", description = "Recupera un producto con sus respectivos enlaces de navegación")
    public EntityModel<Producto> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.obtenerPorId(id);
        return assembler.toModel(producto);
    }

    @GetMapping(value = "/categoria/{categoria}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener productos por sala", description = "Filtra reservas por sala en formato HATEOAS")
    public CollectionModel<EntityModel<Producto>> getProductosByCategoria(@PathVariable String categoria) {
        return CollectionModel.of(
                productoService.buscarPorCategoria(categoria).stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList()),
                linkTo(methodOn(ProductoControllerV2.class).getProductosByCategoria(categoria)).withSelfRel()
        );
    }


    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Registrar un producto (HATEOAS)", description = "Inserta un producto en el catálogo y retorna el recurso interactivo")
    public ResponseEntity<EntityModel<Producto>> createProducto(@RequestBody Producto producto) {
        Producto newProducto = productoService.guardar(producto);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoControllerV2.class).getProductoById(newProducto.getId())).toUri())
                .body(assembler.toModel(newProducto));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un producto (HATEOAS)", description = "Modifica los datos de un producto y retorna el recurso interactivo actualizado")
    public ResponseEntity<EntityModel<Producto>> updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto updatedProducto = productoService.actualizar(id, producto);
        return ResponseEntity
                .ok(assembler.toModel(updatedProducto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar un producto (HATEOAS)", description = "Borra el registro del producto del sistema")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

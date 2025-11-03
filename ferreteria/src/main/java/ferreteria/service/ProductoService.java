/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ferreteria.service;

import ferreteria.domain.Producto;
import ferreteria.repository.CategoriaRepository;
import ferreteria.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired 
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<Producto> getProductos() {
        return productoRepository.findByEstadoTrue();
    }

    @Transactional(readOnly = true)
    public Optional<Producto> getProducto(Long id) {
        return productoRepository.findById(id);
    }

    @Transactional
    public Producto saveOrUpdate(Producto src) {
        // Adjuntar la categoría por su id (viene del form en src.categoria.idCategoria)
        if (src.getCategoria() == null || src.getCategoria().getIdCategoria() == null) {
            throw new IllegalArgumentException("Debe seleccionar una categoría válida.");
        }
        var cat = categoriaRepository.findById(src.getCategoria().getIdCategoria())
                .orElseThrow(() -> new IllegalArgumentException("La categoría no existe."));
        // sustituimos el "cascarón" que mandó el form por la entidad administrada
        src.setCategoria(cat);

        if (src.getIdProducto() != null) {
            // UPDATE: copiamos campos al destino administrado
            var dst = productoRepository.findById(src.getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("El producto no existe"));
            dst.setNombre(src.getNombre());
            dst.setDescripcion(src.getDescripcion());
            dst.setPrecio(src.getPrecio());
            dst.setExistencias(src.getExistencias());
            dst.setRutaImagen(src.getRutaImagen());
            dst.setEstado(src.isEstado());
            dst.setCategoria(cat); // ✅ asignar la relación

            return productoRepository.save(dst);
        } else {
            // CREATE
            src.setEstado(true); // por defecto activo
            return productoRepository.save(src);
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new IllegalArgumentException("El producto con ID " + id + " no existe.");
        }
        try {
            productoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el producto. Tiene datos asociados.", e);
        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ferreteria.repository;


import ferreteria.domain.Producto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    // Devuelve solo los productos con estado = true
    List<Producto> findByEstadoTrue();
    
    // Búsqueda por nombre o parte del nombre
    public List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ferreteria.repository;


import ferreteria.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Devuelve solo los productos activos (por ejemplo, para mostrar en catálogo)
    public List<Producto> findByActivoTrue();

    // Búsqueda por nombre o parte del nombre
    public List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
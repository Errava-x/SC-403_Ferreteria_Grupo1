/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ferreteria.repository;

import ferreteria.domain.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Jairell Bonilla
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByEstadoTrue();
}

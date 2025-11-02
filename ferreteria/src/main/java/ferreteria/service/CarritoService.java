/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ferreteria.service;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ferreteria.domain.CarritoItem;
import ferreteria.domain.Producto;
import ferreteria.repository.ProductoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CarritoService {

    private static final String KEY = "CARRITO_ITEMS";

    @Autowired
    private ProductoRepository productoRepository;

    @SuppressWarnings("unchecked")
    private Map<Long, CarritoItem> getMap(HttpSession session) {
        Object obj = session.getAttribute(KEY);
        if (obj == null) {
            Map<Long, CarritoItem> map = new LinkedHashMap<>();
            session.setAttribute(KEY, map);
            return map;
        }
        return (Map<Long, CarritoItem>) obj;
    }

    public List<CarritoItem> getItems(HttpSession session) {
        return new ArrayList<>(getMap(session).values());
    }

    public BigDecimal getTotal(HttpSession session) {
        return getItems(session).stream()
                .map(CarritoItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void add(HttpSession session, Long productoId, int cantidad) {
        if (cantidad <= 0) cantidad = 1;
        var map = getMap(session);

        Producto p = productoRepository.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no existe"));

        CarritoItem item = map.get(productoId);
        if (item == null) {
            item = new CarritoItem(
                    p.getIdProducto(),
                    p.getNombre(),
                    p.getPrecio(),
                    cantidad,
                    p.getRutaImagen()
            );
            map.put(productoId, item);
        } else {
            item.setCantidad(item.getCantidad() + cantidad);
        }
    }

    public void update(HttpSession session, Long productoId, int nuevaCantidad) {
        var map = getMap(session);
        CarritoItem it = map.get(productoId);
        if (it != null) {
            if (nuevaCantidad <= 0) map.remove(productoId);
            else it.setCantidad(nuevaCantidad);
        }
    }

    public void remove(HttpSession session, Long productoId) {
        getMap(session).remove(productoId);
    }

    public void clear(HttpSession session) {
        getMap(session).clear();
    }
    
    /*Devuelve todos los items del carrito actual almacenados en sesión.*/
    @SuppressWarnings("unchecked")
    public List<CarritoItem> items(HttpSession session) {
        Map<Long, CarritoItem> map = (Map<Long, CarritoItem>) session.getAttribute(KEY);
        if (map == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(map.values());
    }
    
    //Test
    public void seedDemo(HttpSession session) {
        Map<Long, CarritoItem> map = getMap(session);
        map.clear(); // por si ya había algo

        map.put(101L, new CarritoItem(
                101L,
                "Llave Inglesa 10\"",
                new BigDecimal("1700.00"),
                1,
                "/static/img/demo/llave.png"   // opcional, puede ser null
        ));
        map.put(202L, new CarritoItem(
                202L,
                "Galón Pintura Lanco",
                new BigDecimal("34200.00"),
                1,
                "/static/img/demo/pintura.png"
        ));
        map.put(303L, new CarritoItem(
                303L,
                "Brocha Atlas #3",
                new BigDecimal("3100.00"),
                2,
                "/static/img/demo/brocha.png"
        ));

        session.setAttribute(KEY, map);
    }
}
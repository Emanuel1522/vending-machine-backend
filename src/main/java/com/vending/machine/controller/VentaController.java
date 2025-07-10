package com.vending.machine.controller;

import com.vending.machine.helper.EstadoVenta;
import com.vending.machine.model.Venta;
import com.vending.machine.repository.VentaRepository;
import com.vending.machine.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private PagoService pagoService;

    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVentaPorId(@PathVariable Long id) {
        return ventaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Venta> crearVenta(@RequestBody Venta ventaRecibida){
        String codigoQR = pagoService.generarQR(ventaRecibida.getPrecio());

        Venta nuevaVenta = new Venta(
                null,
                ventaRecibida.getProducto(),
                ventaRecibida.getPrecio(),
                EstadoVenta.PENDIENTE,
                codigoQR,
                ventaRecibida.getFechaCreacion()
        );
        Venta guardada = ventaRepository.save(nuevaVenta);
        return ResponseEntity.ok(guardada);
    }

    @GetMapping("/{id}/estado")
    public ResponseEntity<?> obtenerEstadoVenta(@PathVariable Long id) {
        return ventaRepository.findById(id)
                .map(venta -> {
                    if (venta.getEstado() == EstadoVenta.PAGADO){
                        liberarProducto(venta.getId());
                    } else if (venta.getEstado() == EstadoVenta.PENDIENTE){
                        LocalDateTime ahora = LocalDateTime.now();
                        if (venta.getFechaCreacion().plusMinutes(3).isBefore(ahora)) {
                            venta.setEstado(EstadoVenta.EXPIRADO);
                            ventaRepository.save(venta);
                        }
                    }
                    return ResponseEntity.ok().body(
                            Map.of("estado", venta.getEstado().name())
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/verificar-pago")
    public ResponseEntity<Venta> verificarPago (@PathVariable Long id){
        return ventaRepository.findById(id)
                .map(venta -> {
                    if (venta.getEstado() == EstadoVenta.PENDIENTE) {
                        venta.setEstado(EstadoVenta.PAGADO);
                        ventaRepository.save(venta);
                        System.out.println("Pago verificado para venta con ID " + id);
                    }
                    return ResponseEntity.ok(venta);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/liberar")
    public ResponseEntity<Map<String, String>> liberarProducto(@PathVariable Long id){
        return ventaRepository.findById(id)
                .map(venta -> {
                  if (venta.getEstado() == EstadoVenta.PAGADO) {
                      envioArduino(venta.getProducto());
                      return ResponseEntity.ok(Map.of("orden", "LIBERAR"));
                  } else {
                      return ResponseEntity.ok(Map.of("orden", "NO_LIBERAR"));
                  }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public void envioArduino(String producto){
        System.out.println("Se ha liberado el producto!");
    }
}

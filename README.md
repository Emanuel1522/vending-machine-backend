# 🥤 Máquina Expendedora – Backend con Spring Boot

Este proyecto simula el backend de una **máquina expendedora** usando Java con Spring Boot. Permite registrar una venta, generar un código QR (simulado) para el pago, verificar el estado de la venta y liberar automáticamente el producto si se confirma el pago. También maneja la **expiración automática** si no se realiza el pago en un tiempo límite.

---

## 🚀 Tecnologías utilizadas

- Java 17  
- Spring Boot 3  
- Arquitectura por capas  
- H2 (base de datos en memoria)  
- Postman (para pruebas)  

---

## ⚙️ Funcionalidades principales

### ✅ Crear venta  
Registra una venta nueva en estado `PENDIENTE` y genera un código QR simulado para el pago.

`POST /venta`  
```json
{
  "producto": "Galletas",
  "precio": 5.00
}
```
### 🔄 Obtener estado de la venta
Consulta el estado actual (PENDIENTE, PAGADO o EXPIRADO). Si han pasado más de 3 minutos desde la creación y no se ha pagado, se marca automáticamente como EXPIRADO.
```GET /venta/{id}/estado```

### 💰 Verificar pago
Cambia el estado de la venta a PAGADO. En un sistema real, esta operación vendría desde la confirmación del proveedor de pagos (como Izipay).
```PUT /venta/{id}/verificar-pago```

### 🚪 Liberar producto
Si la venta ya fue pagada, ejecuta una simulación de liberación de producto (a través de impresión por consola).
```GET /venta/{id}/liberar```

---

## 🔌 Integraciones previstas

- 🔄 Izipay (API de pagos) → Actualmente simulada. El sistema está listo para integrar una pasarela de pago real.
- 🔧 Arduino (control físico) → La orden de liberación está lista para conectarse con un dispositivo físico vía puerto serie o protocolo definido.

---

## 📦 Estructura del proyecto

```
📁 vending-machine
├── controller/
│   └── VentaController.java
├── model/
│   └── Venta.java
├── repository/
│   └── VentaRepository.java
├── service/
│   └── PagoService.java
├── helper/
│   └── EstadoVenta.java
└── VendingMachineApplication.java
```
---

## 📍 Simulación del flujo completo

- El frontend envía una solicitud para crear una venta.
- Se genera un QR y se muestra en una tablet.
- El frontend consulta constantemente el estado de la venta.
- Si se paga, se libera el producto automáticamente.
- Si pasan más de 3 minutos sin pagar, se marca como expirado y se elimina.

---

## ✅ Estado actual
✔️ Backend funcional y probado en Postman
🧩 Pendiente integración con Izipay y Arduino físico

---

## 📬 Autor
Desarrollado por Emanuel Rojas Ramirez

[🔗 Portafolio](https://emanuel-rojas-page.vercel.app) | [LinkedIn](https://www.linkedin.com/in/emanuel-rojas-ramirez-0b187835a/)

---

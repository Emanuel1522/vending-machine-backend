package com.vending.machine.service;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class PagoService {

    public String generarQR(double monto){
        //Aqui se conectaria con la api

        System.out.println("Se genera QR por valor de: " + monto);
        return "https://fake-qr.com/pago/" + UUID.randomUUID();
    }
}

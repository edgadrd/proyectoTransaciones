package com.nttdata.transaction.client;

import com.nttdata.transaction.model.entity.dto.CuentaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(value = "feignCuenta", url = "http://localhost:8081/cuenta/cuenta/")
public interface IFeignCuenta {


    @GetMapping(value = "/{id}")
    CuentaResponse obtenerCuenta(@PathVariable("id") Long id);

    @PutMapping(value = "/{id}/depositar")
    CuentaResponse depositar(@PathVariable("id") Long id, @RequestParam("monto") Double amount);

    @PutMapping(value = "/{id}/retirar")
    CuentaResponse retirar(@PathVariable("id") Long id, @RequestParam("monto") Double amount);

//    @GetMapping(value = "/cliente/{clienteId}/exists")
//    boolean clienteCuenta(@PathVariable("clienteId") Long clienteId);
}

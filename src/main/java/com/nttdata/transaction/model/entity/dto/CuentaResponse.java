package com.nttdata.transaction.model.entity.dto;

import com.nttdata.transaction.model.entity.TipoCuenta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CuentaResponse {
    private Long id;
    private String numeroCuenta;
    private Double saldo;
    private TipoCuenta tipoCuenta;
    private Long clienteId;
}


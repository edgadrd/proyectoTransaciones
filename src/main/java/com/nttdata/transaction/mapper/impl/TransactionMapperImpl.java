package com.nttdata.transaction.mapper.impl;

import com.nttdata.transaction.mapper.TransactionMapper;
import com.nttdata.transaction.model.*;
import com.nttdata.transaction.model.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapperImpl implements TransactionMapper {
    @Override
    public TransaccionDepositoResponse transactionToDeposito(Transaction transaction) {
         TransaccionDepositoResponse response = new TransaccionDepositoResponse();
         response.id(transaction.getId());
         response.cuentaId(transaction.getCuentaDestinoId());
         response.monto(transaction.getMonto());
         response.fecha(transaction.getFecha());
         response.tipo(transaction.getTipo());
        return response;
    }

    @Override
    public TransaccionRetiroResponse transactionToRetiro(Transaction transaction) {
        TransaccionRetiroResponse response = new TransaccionRetiroResponse();
        response.id(transaction.getId());
        response.cuentaId(transaction.getCuentaDestinoId());
        response.monto(transaction.getMonto());
        response.fecha(transaction.getFecha());
        response.tipo(transaction.getTipo());

        return response;
    }

    @Override
    public TransaccionTransferenciaResponse transactionToTransferencia(Transaction transaction) {
        TransaccionTransferenciaResponse response = new TransaccionTransferenciaResponse();
        response.id(transaction.getId());
        response.cuentaOrigenId(transaction.getCuentaOrigenId());
        response.cuentaDestinoId(transaction.getCuentaDestinoId());
        response.monto(transaction.getMonto());
        response.fecha(transaction.getFecha());
        response.tipo(transaction.getTipo());
        return response;
    }

    @Override
    public TransaccionHistorialResponse transactionToHistorialDto(Transaction transaction) {
        TransaccionHistorialResponse response = new TransaccionHistorialResponse();
        response.id(transaction.getId());
        response.cuentaOrigenId(transaction.getCuentaOrigenId());
        response.cuentaDestinoId(transaction.getCuentaDestinoId());
        response.monto(transaction.getMonto());
        response.fecha(transaction.getFecha());
        response.setTipo(transaction.getTipo());
        return response;
    }
}

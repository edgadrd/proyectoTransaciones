package com.nttdata.transaction;

import com.nttdata.transaction.api.TransaccionesApiDelegate;
import com.nttdata.transaction.business.TransactionService;
import com.nttdata.transaction.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class TransactionDelegateImpl implements TransaccionesApiDelegate {

    private final TransactionService transactionService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return TransaccionesApiDelegate.super.getRequest();
    }

    @Override
    @PostMapping("/transacciones/deposito")
    public ResponseEntity<TransaccionDepositoResponse> transaccionesDepositoPost(TransaccionDepositoRequest transaccionDepositoRequest) {
        return new ResponseEntity<>(transactionService.realizarDeposito(transaccionDepositoRequest), HttpStatus.OK);

    }

    @Override
    @GetMapping("/transacciones/historial")
    public ResponseEntity<List<TransaccionHistorialResponse>> transaccionesHistorialGet() {
        return new ResponseEntity<>(transactionService.obtenerHistorialTransacciones(), HttpStatus.OK);
    }

    @Override
    @PostMapping("/transacciones/retiro")
    public ResponseEntity<TransaccionRetiroResponse> transaccionesRetiroPost(TransaccionRetiroRequest transaccionRetiroRequest) {
        return new ResponseEntity<>(transactionService.realizarRetiro(transaccionRetiroRequest), HttpStatus.OK);
    }

    @Override
    @PostMapping("transacciones/transferencia")
    public ResponseEntity<TransaccionTransferenciaResponse> transaccionesTransferenciaPost(TransaccionTransferenciaRequest transaccionTransferenciaRequest) {
        return new ResponseEntity<>(transactionService.realizarTransferencia(transaccionTransferenciaRequest), HttpStatus.OK);
    }
}

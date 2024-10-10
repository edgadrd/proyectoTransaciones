package com.nttdata.transaction.business;

import com.nttdata.transaction.model.*;

import java.util.List;

public interface TransactionService {

    TransaccionDepositoResponse realizarDeposito(TransaccionDepositoRequest depositoDto);

    TransaccionRetiroResponse realizarRetiro(TransaccionRetiroRequest retiroDto);

    TransaccionTransferenciaResponse realizarTransferencia(TransaccionTransferenciaRequest transferenciaDto);

    List<TransaccionHistorialResponse> obtenerHistorialTransacciones();
}

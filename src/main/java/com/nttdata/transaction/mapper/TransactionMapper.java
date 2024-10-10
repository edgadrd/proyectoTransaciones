package com.nttdata.transaction.mapper;

import com.nttdata.transaction.model.*;
import com.nttdata.transaction.model.entity.Transaction;


public interface TransactionMapper {

    TransaccionDepositoResponse transactionToDeposito (Transaction transaction);

    TransaccionRetiroResponse transactionToRetiro (Transaction transaction);
    
    TransaccionTransferenciaResponse transactionToTransferencia (Transaction transaction);

    TransaccionHistorialResponse transactionToHistorialDto(Transaction transaction);

}

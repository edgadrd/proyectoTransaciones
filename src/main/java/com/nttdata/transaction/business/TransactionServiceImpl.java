package com.nttdata.transaction.business;

import com.nttdata.transaction.client.IFeignCuenta;
import com.nttdata.transaction.mapper.TransactionMapper;
import com.nttdata.transaction.model.*;
import com.nttdata.transaction.model.entity.Transaction;
import com.nttdata.transaction.model.entity.TipoEnum;
import com.nttdata.transaction.model.entity.dto.*;
import com.nttdata.transaction.repository.TransactionRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final IFeignCuenta iFeignCuenta;


    @Override
    public TransaccionDepositoResponse realizarDeposito(TransaccionDepositoRequest depositoRequest) {
        try {

            CuentaResponse cuenta =  iFeignCuenta.obtenerCuenta(depositoRequest.getCuentaId());
            if (cuenta == null) {
                throw new RuntimeException("La cuenta no existe");
            }

            iFeignCuenta.depositar(cuenta.getId(), depositoRequest.getMonto());
            Transaction deposito = Transaction.builder()
                    .tipo(String.valueOf(TipoEnum.DEPOSITO))
                    .monto(depositoRequest.getMonto())
                    .cuentaDestinoId(depositoRequest.getCuentaId())
                    .fecha(LocalDate.now())
                    .build();

            return transactionMapper.transactionToDeposito(transactionRepository.save(deposito));

        } catch (FeignException e) {

            throw new RuntimeException("Error al consultar cuentas: " + e.getMessage());
        } catch (Exception e) {

            throw new RuntimeException("Error al registrar el depósito: " + e.getMessage());
        }
    }

    @Override
    public TransaccionRetiroResponse realizarRetiro(TransaccionRetiroRequest retiroRequest) {
        try {

            CuentaResponse cuenta = iFeignCuenta.obtenerCuenta(retiroRequest.getCuentaId());
            if (cuenta == null) {
                throw new RuntimeException("La cuenta no existe");
            }

            iFeignCuenta.retirar(retiroRequest.getCuentaId(), retiroRequest.getMonto());

            Transaction retiro = Transaction.builder()
                    .cuentaOrigenId(retiroRequest.getCuentaId())
                    .monto(retiroRequest.getMonto())
                    .tipo(String.valueOf(TipoEnum.RETIRO))
                    .fecha(LocalDate.now())
                    .build();

            return transactionMapper.transactionToRetiro((transactionRepository.save(retiro)));

        } catch (FeignException e) {

            throw new RuntimeException("Error al consultar cuentas: " + e.getMessage());
        } catch (Exception e) {

            throw new RuntimeException("Error al registrar el retiro: " + e.getMessage());
        }

    }

    @Override
    public TransaccionTransferenciaResponse realizarTransferencia(TransaccionTransferenciaRequest transferenciaRequest) {

        try {
            CuentaResponse cuentaOrigen =iFeignCuenta.obtenerCuenta(transferenciaRequest.getCuentaOrigenId());

            if (cuentaOrigen == null) {
                throw new RuntimeException("La cuenta Origen no existe");
            }
            iFeignCuenta.retirar(cuentaOrigen.getId(), transferenciaRequest.getMonto());


            CuentaResponse cuentaDestino =iFeignCuenta.obtenerCuenta(transferenciaRequest.getCuentaDestinoId());

            if (cuentaDestino == null) {
                throw new RuntimeException("La cuenta destino no existe");
            }

            iFeignCuenta.depositar(cuentaDestino.getId(), transferenciaRequest.getMonto());

            Transaction transferencia = Transaction.builder()
                    .cuentaOrigenId(cuentaOrigen.getId())
                    .cuentaDestinoId(cuentaDestino.getId())
                    .monto(transferenciaRequest.getMonto())
                    .tipo(String.valueOf(TipoEnum.TRANSFERENCIA))
                    .fecha(LocalDate.now())
                    .build();

            return transactionMapper.transactionToTransferencia(transactionRepository.save(transferencia));

        } catch (FeignException e) {

            throw new RuntimeException("Error al consultar cuentas: " + e.getMessage());
        } catch (Exception e) {

            throw new RuntimeException("Error al registrar el retiro: " + e.getMessage());
        }
    }

    @Override
    public List<TransaccionHistorialResponse> obtenerHistorialTransacciones() {
        return transactionRepository.findAll().stream()
                .map(transactionMapper::transactionToHistorialDto).collect(Collectors.toList());
    }
}

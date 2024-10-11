package com.nttdata.transaction.business;

import com.nttdata.transaction.client.IFeignCuenta;
import com.nttdata.transaction.mapper.TransactionMapper;
import com.nttdata.transaction.mapper.impl.TransactionMapperImpl;
import com.nttdata.transaction.model.*;
import com.nttdata.transaction.model.entity.TipoEnum;
import com.nttdata.transaction.model.entity.Transaction;
import com.nttdata.transaction.model.entity.dto.*;
import com.nttdata.transaction.repository.TransactionRepository;
import com.nttdata.transaction.util.*;

import feign.FeignException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;





@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
//hollaaa
    @Mock
    private  TransactionRepository transactionRepository;

    private TransactionMapper transactionMapper;

    @Mock
    private IFeignCuenta iFeignCuenta;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private TransaccionDepositoRequest  transaccionDepositoRequest;

    private List<TransaccionDepositoResponse>  transaccionDepositoResponse;

    private TransaccionRetiroRequest transaccionRetiroRequest;

    private List<TransaccionRetiroResponse>   transaccionRetiroResponse;

    private TransaccionTransferenciaRequest transaccionTransferenciaRequest;

    private List<TransaccionTransferenciaResponse>   transaccionTransferenciaResponse;

    private List<TransaccionHistorialResponse>  transaccionHistorialResponse;

    private CuentaResponse cuentaResponse;

    private List<Transaction> transaction;


    @BeforeEach
    void setUp() throws IOException {
        transactionMapper = new TransactionMapperImpl();
        transactionService = new TransactionServiceImpl(transactionRepository, transactionMapper, iFeignCuenta);
        transaccionDepositoRequest = TransaccionDepositoJsonReader.readTransaccionDepositoRequestFromJson("src/test/java/com/nttdata/transaction/resources/transaccionDepositoRequest.json");
        transaccionDepositoResponse = TransaccionDepositoJsonReader.readTransaccionDepositoResponseFromJson("src/test/java/com/nttdata/transaction/resources/transaccionDepositoResponse.json");
        transaccionRetiroRequest = TransaccionRetiroJsonReader.readTransaccionRetiroRequestFromJson("src/test/java/com/nttdata/transaction/resources/transaccionRetiroRequest.json");
        transaccionRetiroResponse = TransaccionRetiroJsonReader.readTransaccionRetiroResponseFromJson("src/test/java/com/nttdata/transaction/resources/transaccionRetiroResponse.json");
        transaccionTransferenciaRequest = TransaccionTransferenciaJsonReader.readTransaccionTransferenciaRequestFromJson("src/test/java/com/nttdata/transaction/resources/transaccionTransferenciaRequest.json");
        transaccionTransferenciaResponse = TransaccionTransferenciaJsonReader.readTransaccionTransferenciaResponseFromJson("src/test/java/com/nttdata/transaction/resources/transaccionTransferenciaResponse.json");
        transaccionHistorialResponse = TransaccionHistorialJsonReader.readTransaccionHistorialResponseFromJson("src/test/java/com/nttdata/transaction/resources/transaccionHistorialResponse.json");
        cuentaResponse = CuentaResponseJsonReader.readCuentaResponseFromJson("src/test/java/com/nttdata/transaction/resources/cuentaResponse.json");
        transaction = TransactionJsonReader.readTransactionFromJson("src/test/java/com/nttdata/transaction/resources/transactionEntity.json");
    }

    @Test
    void testRealizarDeposito() {


        when(iFeignCuenta.obtenerCuenta(transaccionDepositoRequest.getCuentaId())).thenReturn(cuentaResponse);
        when(iFeignCuenta.depositar(cuentaResponse.getId(), transaccionDepositoRequest.getMonto())).thenReturn(cuentaResponse);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction.get(0));

        TransaccionDepositoResponse result = transactionService.realizarDeposito(transaccionDepositoRequest);
        
        assertNotNull(result);
        assertNotNull(result.getCuentaId());

        assertEquals(TipoEnum.DEPOSITO.toString(), result.getTipo());
        verify(iFeignCuenta, times(1)).obtenerCuenta(cuentaResponse.getId());
        verify(iFeignCuenta, times(1)).depositar(cuentaResponse.getId(), transaccionDepositoRequest.getMonto());
        verify(transactionRepository, times(1)).save(any(Transaction.class));

    }

    @Test
    void testRealizarRetiro() {
       

        when(iFeignCuenta.obtenerCuenta(transaccionRetiroRequest.getCuentaId())).thenReturn(cuentaResponse);
        when(iFeignCuenta.retirar(cuentaResponse.getId(), transaccionRetiroRequest.getMonto())).thenReturn(cuentaResponse);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction.get(1));
        

        TransaccionRetiroResponse result = transactionService.realizarRetiro(transaccionRetiroRequest);


        assertNotNull(result);
        assertEquals(TipoEnum.RETIRO.toString(), result.getTipo());
        verify(iFeignCuenta, times(1)).obtenerCuenta(transaccionRetiroRequest.getCuentaId());
        verify(iFeignCuenta, times(1)).retirar(transaccionRetiroRequest.getCuentaId(), transaccionRetiroRequest.getMonto());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        
    }

   @Test
    void testRealizarTransferencia() {

        when(iFeignCuenta.obtenerCuenta(transaccionTransferenciaRequest.getCuentaOrigenId())).thenReturn(cuentaResponse);
        when(iFeignCuenta.obtenerCuenta(transaccionTransferenciaRequest.getCuentaDestinoId())).thenReturn(cuentaResponse);
        when(iFeignCuenta.retirar(cuentaResponse.getId(), transaccionTransferenciaRequest.getMonto())).thenReturn(cuentaResponse);
        when(iFeignCuenta.depositar(cuentaResponse.getId(), transaccionTransferenciaRequest.getMonto())).thenReturn(cuentaResponse);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction.get(2));

        TransaccionTransferenciaResponse result = transactionService.realizarTransferencia(transaccionTransferenciaRequest);

        assertNotNull(result);
        assertEquals(TipoEnum.TRANSFERENCIA.toString(), result.getTipo());
        verify(iFeignCuenta, times(1)).obtenerCuenta(transaccionTransferenciaRequest.getCuentaOrigenId());
        verify(iFeignCuenta, times(1)).obtenerCuenta(transaccionTransferenciaRequest.getCuentaDestinoId());
        verify(iFeignCuenta, times(1)).retirar(cuentaResponse.getId(), transaccionTransferenciaRequest.getMonto());
        verify(iFeignCuenta, times(1)).depositar(cuentaResponse.getId(), transaccionTransferenciaRequest.getMonto());
        verify(transactionRepository, times(1)).save(any(Transaction.class));


        
    }

    @Test
    void testObtenerHistorialTransacciones() {
       
        when(transactionRepository.findAll()).thenReturn(transaction);

        List<TransaccionHistorialResponse> result = transactionService.obtenerHistorialTransacciones();

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(transactionRepository, times(1)).findAll();

        
    }

    @Test
void testRealizarDepositoCuentaNoExiste() {
    when(iFeignCuenta.obtenerCuenta(transaccionDepositoRequest.getCuentaId())).thenReturn(null);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        transactionService.realizarDeposito(transaccionDepositoRequest);
    });

    assertEquals("Error al registrar el depósito: La cuenta no existe", exception.getMessage());
    verify(iFeignCuenta, times(1)).obtenerCuenta(transaccionDepositoRequest.getCuentaId());
    verify(iFeignCuenta, times(0)).depositar(anyLong(), anyDouble());
    verify(transactionRepository, times(0)).save(any(Transaction.class));
}

@Test
void testRealizarDepositoFeignException() {
    when(iFeignCuenta.obtenerCuenta(transaccionDepositoRequest.getCuentaId())).thenThrow(FeignException.class);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        transactionService.realizarDeposito(transaccionDepositoRequest);
    });

    assertTrue(exception.getMessage().contains("Error al consultar cuentas"));
    verify(iFeignCuenta, times(1)).obtenerCuenta(transaccionDepositoRequest.getCuentaId());
    verify(iFeignCuenta, times(0)).depositar(anyLong(), anyDouble());
    verify(transactionRepository, times(0)).save(any(Transaction.class));
}

@Test
void testRealizarRetiroCuentaNoExiste() {
    when(iFeignCuenta.obtenerCuenta(transaccionRetiroRequest.getCuentaId())).thenReturn(null);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        transactionService.realizarRetiro(transaccionRetiroRequest);
    });

    assertEquals("Error al registrar el retiro: La cuenta no existe", exception.getMessage());
    verify(iFeignCuenta, times(1)).obtenerCuenta(transaccionRetiroRequest.getCuentaId());
    verify(iFeignCuenta, times(0)).retirar(anyLong(), anyDouble());
    verify(transactionRepository, times(0)).save(any(Transaction.class));
}

@Test
void testRealizarRetiroFeignException() {
    when(iFeignCuenta.obtenerCuenta(transaccionRetiroRequest.getCuentaId())).thenThrow(FeignException.class);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        transactionService.realizarRetiro(transaccionRetiroRequest);
    });

    assertTrue(exception.getMessage().contains("Error al consultar cuentas"));
    verify(iFeignCuenta, times(1)).obtenerCuenta(transaccionRetiroRequest.getCuentaId());
    verify(iFeignCuenta, times(0)).retirar(anyLong(), anyDouble());
    verify(transactionRepository, times(0)).save(any(Transaction.class));
}

@Test
void testRealizarTransferenciaCuentaOrigenNoExiste() {
    when(iFeignCuenta.obtenerCuenta(transaccionTransferenciaRequest.getCuentaOrigenId())).thenReturn(null);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        transactionService.realizarTransferencia(transaccionTransferenciaRequest);
    });

    assertEquals("Error al registrar el retiro: La cuenta Origen no existe", exception.getMessage());
    verify(iFeignCuenta, times(1)).obtenerCuenta(transaccionTransferenciaRequest.getCuentaOrigenId());
    verify(iFeignCuenta, times(0)).retirar(anyLong(), anyDouble());
    verify(iFeignCuenta, times(0)).obtenerCuenta(transaccionTransferenciaRequest.getCuentaDestinoId());
    verify(iFeignCuenta, times(0)).depositar(anyLong(), anyDouble());
    verify(transactionRepository, times(0)).save(any(Transaction.class));
}

@Test
void testRealizarTransferenciaCuentaDestinoNoExiste() {
    when(iFeignCuenta.obtenerCuenta(transaccionTransferenciaRequest.getCuentaOrigenId())).thenReturn(cuentaResponse);
    when(iFeignCuenta.obtenerCuenta(transaccionTransferenciaRequest.getCuentaDestinoId())).thenReturn(null);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        transactionService.realizarTransferencia(transaccionTransferenciaRequest);
    });

    assertEquals("Error al registrar el retiro: La cuenta destino no existe", exception.getMessage());
    verify(iFeignCuenta, times(1)).obtenerCuenta(transaccionTransferenciaRequest.getCuentaOrigenId());
    verify(iFeignCuenta, times(1)).obtenerCuenta(transaccionTransferenciaRequest.getCuentaDestinoId());
    verify(iFeignCuenta, times(0)).depositar(anyLong(), anyDouble());
    verify(transactionRepository, times(0)).save(any(Transaction.class));
}

@Test
void testRealizarTransferenciaFeignException() {
    when(iFeignCuenta.obtenerCuenta(transaccionTransferenciaRequest.getCuentaOrigenId())).thenThrow(FeignException.class);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        transactionService.realizarTransferencia(transaccionTransferenciaRequest);
    });

    assertTrue(exception.getMessage().contains("Error al consultar cuentas"));
    verify(iFeignCuenta, times(1)).obtenerCuenta(transaccionTransferenciaRequest.getCuentaOrigenId());
    verify(iFeignCuenta, times(0)).retirar(anyLong(), anyDouble());
    verify(iFeignCuenta, times(0)).obtenerCuenta(transaccionTransferenciaRequest.getCuentaDestinoId());
    verify(iFeignCuenta, times(0)).depositar(anyLong(), anyDouble());
    verify(transactionRepository, times(0)).save(any(Transaction.class));
}
}

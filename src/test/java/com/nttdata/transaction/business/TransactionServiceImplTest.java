package com.nttdata.transaction.business;

import com.nttdata.transaction.client.IFeignCuenta;
import com.nttdata.transaction.mapper.TransactionMapper;
import com.nttdata.transaction.mapper.impl.TransactionMapperImpl;
import com.nttdata.transaction.model.*;
import com.nttdata.transaction.model.entity.TipoEnum;
import com.nttdata.transaction.model.entity.Transaction;
import com.nttdata.transaction.model.entity.dto.*;
import com.nttdata.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;





@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    
    private TransactionMapper transactionMapper;

    @Mock
    private IFeignCuenta iFeignCuenta;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {

        transactionMapper = new TransactionMapperImpl();
        transactionService = new TransactionServiceImpl(transactionRepository, transactionMapper, iFeignCuenta);
    }

    @Test
    void testRealizarDeposito() {
        TransaccionDepositoRequest request = new TransaccionDepositoRequest();
        request.setCuentaId(1L);
        request.setMonto(100.0);

        CuentaResponse cuentaResponse = new CuentaResponse();
        cuentaResponse.setId(1L);

        Transaction transaction = new Transaction();
        transaction.setId("1");

        TransaccionDepositoResponse response = new TransaccionDepositoResponse();

        when(iFeignCuenta.obtenerCuenta(request.getCuentaId())).thenReturn(cuentaResponse);
        when(iFeignCuenta.depositar(anyLong(), anyDouble())).thenReturn(cuentaResponse);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
    

        TransaccionDepositoResponse result = transactionService.realizarDeposito(request);
    
        assertNotNull(result);

        assertEquals(TipoEnum.DEPOSITO, result.getTipo());
        assertEquals(100.0, result.getMonto());

        verify(iFeignCuenta, times(1)).obtenerCuenta(request.getCuentaId());
        verify(iFeignCuenta, times(1)).depositar(cuentaResponse.getId(), request.getMonto());
        verify(transactionRepository, times(1)).save(any(Transaction.class));


        
    }

    @Test
    void testRealizarRetiro() {
        TransaccionRetiroRequest request = new TransaccionRetiroRequest();
        request.setCuentaId(1L);
        request.setMonto(50.0);

        CuentaResponse cuentaResponse = new CuentaResponse();
        cuentaResponse.setId(1L);

        Transaction transaction = new Transaction();
        transaction.setId("1");

        TransaccionRetiroResponse response = new TransaccionRetiroResponse();

        when(iFeignCuenta.obtenerCuenta(request.getCuentaId())).thenReturn(cuentaResponse);
        when(iFeignCuenta.retirar(anyLong(), anyDouble())).thenReturn(cuentaResponse);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        

        TransaccionRetiroResponse result = transactionService.realizarRetiro(request);



        assertNotNull(result);
        verify(iFeignCuenta, times(1)).obtenerCuenta(request.getCuentaId());
        verify(iFeignCuenta, times(1)).retirar(request.getCuentaId(), request.getMonto());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        
    }

   /* @Test
    void testRealizarTransferencia() {
        TransaccionTransferenciaRequest request = new TransaccionTransferenciaRequest();
        request.setCuentaOrigenId(1L);
        request.setCuentaDestinoId(2L);
        request.setMonto(200.0);

        CuentaResponse cuentaOrigen = new CuentaResponse();
        cuentaOrigen.setId(1L);

        CuentaResponse cuentaDestino = new CuentaResponse();
        cuentaDestino.setId(2L);

        Transaction transaction = new Transaction();
        transaction.setId("1");

        TransaccionTransferenciaResponse response = new TransaccionTransferenciaResponse();

        when(iFeignCuenta.obtenerCuenta(request.getCuentaOrigenId())).thenReturn(cuentaOrigen);
        
        when(iFeignCuenta.retirar(anyLong(), anyDouble())).thenReturn(cuentaOrigen);
        when(iFeignCuenta.depositar(anyLong(), anyDouble())).thenReturn(cuentaDestino);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        

        TransaccionTransferenciaResponse result = transactionService.realizarTransferencia(request);

        assertNotNull(result);
        verify(iFeignCuenta, times(1)).obtenerCuenta(request.getCuentaOrigenId());
        verify(iFeignCuenta, times(1)).obtenerCuenta(request.getCuentaDestinoId());
        verify(iFeignCuenta, times(1)).retirar(cuentaOrigen.getId(), request.getMonto());
        verify(iFeignCuenta, times(1)).depositar(cuentaDestino.getId(), request.getMonto());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        
    }*/

    @Test
    void testObtenerHistorialTransacciones() {
        Transaction transaction = new Transaction();
        transaction.setId("1");

        TransaccionHistorialResponse response = new TransaccionHistorialResponse();

        when(transactionRepository.findAll()).thenReturn(Collections.singletonList(transaction));
        
        List<TransaccionHistorialResponse> result = transactionService.obtenerHistorialTransacciones();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(transactionRepository, times(1)).findAll();
        
    }
}
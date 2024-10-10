package com.nttdata.transaction; 

import com.nttdata.transaction.business.TransactionService;
import com.nttdata.transaction.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith (MockitoExtension.class)
public class TransactionDelegateImplTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionDelegateImpl transactionDelegate;

    @BeforeEach
    public void setUp() {
        
    }

    @Test
    public void testGetRequest() {
        Optional<NativeWebRequest> request = transactionDelegate.getRequest();
        assertEquals(Optional.empty(), request);
    }

    @Test
    public void testTransaccionesDepositoPost() {
        TransaccionDepositoRequest request = new TransaccionDepositoRequest();
        TransaccionDepositoResponse response = new TransaccionDepositoResponse();
        when(transactionService.realizarDeposito(request)).thenReturn(response);

        ResponseEntity<TransaccionDepositoResponse> result = transactionDelegate.transaccionesDepositoPost(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(transactionService, times(1)).realizarDeposito(request);
    }

    @Test
    public void testTransaccionesHistorialGet() {
        List<TransaccionHistorialResponse> response = List.of(new TransaccionHistorialResponse());
        when(transactionService.obtenerHistorialTransacciones()).thenReturn(response);

        ResponseEntity<List<TransaccionHistorialResponse>> result = transactionDelegate.transaccionesHistorialGet();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(transactionService, times(1)).obtenerHistorialTransacciones();
    }

    @Test
    public void testTransaccionesRetiroPost() {
        TransaccionRetiroRequest request = new TransaccionRetiroRequest();
        TransaccionRetiroResponse response = new TransaccionRetiroResponse();
        when(transactionService.realizarRetiro(request)).thenReturn(response);

        ResponseEntity<TransaccionRetiroResponse> result = transactionDelegate.transaccionesRetiroPost(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(transactionService, times(1)).realizarRetiro(request);
    }

    @Test
    public void testTransaccionesTransferenciaPost() {
        TransaccionTransferenciaRequest request = new TransaccionTransferenciaRequest();
        TransaccionTransferenciaResponse response = new TransaccionTransferenciaResponse();
        when(transactionService.realizarTransferencia(request)).thenReturn(response);

        ResponseEntity<TransaccionTransferenciaResponse> result = transactionDelegate.transaccionesTransferenciaPost(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(transactionService, times(1)).realizarTransferencia(request);
    }
}
package com.nttdata.transaction;

import com.nttdata.transaction.business.TransactionService;
import com.nttdata.transaction.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionDelegateImpl.class)
public class TransactionDelegateImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTransaccionesDepositoPost() throws Exception {
        TransaccionDepositoRequest request = new TransaccionDepositoRequest();
        TransaccionDepositoResponse response = new TransaccionDepositoResponse();

        when(transactionService.realizarDeposito(request)).thenReturn(response);

        mockMvc.perform(post("/transacciones/deposito")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ /* JSON representation of TransaccionDepositoRequest */ }"))
                .andExpect(status().isOk())
                .andExpect(content().json("{ /* JSON representation of TransaccionDepositoResponse */ }"));
    }

    @Test
    public void testTransaccionesHistorialGet() throws Exception {
        List<TransaccionHistorialResponse> response = Collections.singletonList(new TransaccionHistorialResponse());

        when(transactionService.obtenerHistorialTransacciones()).thenReturn(response);

        mockMvc.perform(get("/transacciones/historial"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{ /* JSON representation of TransaccionHistorialResponse */ }]"));
    }

    @Test
    public void testTransaccionesRetiroPost() throws Exception {
        TransaccionRetiroRequest request = new TransaccionRetiroRequest();
        TransaccionRetiroResponse response = new TransaccionRetiroResponse();

        when(transactionService.realizarRetiro(request)).thenReturn(response);

        mockMvc.perform(post("/transacciones/retiro")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ /* JSON representation of TransaccionRetiroRequest */ }"))
                .andExpect(status().isOk())
                .andExpect(content().json("{ /* JSON representation of TransaccionRetiroResponse */ }"));
    }

    @Test
    public void testTransaccionesTransferenciaPost() throws Exception {
        TransaccionTransferenciaRequest request = new TransaccionTransferenciaRequest();
        TransaccionTransferenciaResponse response = new TransaccionTransferenciaResponse();

        when(transactionService.realizarTransferencia(request)).thenReturn(response);

        mockMvc.perform(post("/transacciones/transferencia")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ /* JSON representation of TransaccionTransferenciaRequest */ }"))
                .andExpect(status().isOk())
                .andExpect(content().json("{ /* JSON representation of TransaccionTransferenciaResponse */ }"));
    }
}
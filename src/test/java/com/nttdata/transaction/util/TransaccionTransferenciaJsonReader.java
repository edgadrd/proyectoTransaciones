package com.nttdata.transaction.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.transaction.model.TransaccionDepositoRequest;
import com.nttdata.transaction.model.TransaccionDepositoResponse;
import com.nttdata.transaction.model.TransaccionTransferenciaRequest;
import com.nttdata.transaction.model.TransaccionTransferenciaResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TransaccionTransferenciaJsonReader {

    public static TransaccionTransferenciaRequest readTransaccionTransferenciaRequestFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), new TypeReference<TransaccionTransferenciaRequest>() {});
    }

    public static List<TransaccionTransferenciaResponse> readTransaccionTransferenciaResponseFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), new TypeReference<List<TransaccionTransferenciaResponse>>() {});
    }
}

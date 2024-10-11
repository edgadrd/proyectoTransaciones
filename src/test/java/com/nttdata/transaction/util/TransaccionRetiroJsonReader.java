package com.nttdata.transaction.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.transaction.model.TransaccionHistorialResponse;
import com.nttdata.transaction.model.TransaccionRetiroRequest;
import com.nttdata.transaction.model.TransaccionRetiroResponse;
import com.nttdata.transaction.model.TransaccionTransferenciaRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TransaccionRetiroJsonReader {

    public static TransaccionRetiroRequest readTransaccionRetiroRequestFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), new TypeReference<TransaccionRetiroRequest>() {});
    }

    public static List<TransaccionRetiroResponse> readTransaccionRetiroResponseFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), new TypeReference<List<TransaccionRetiroResponse>>() {});
    }
}

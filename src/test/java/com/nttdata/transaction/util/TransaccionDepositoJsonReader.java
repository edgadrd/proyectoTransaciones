package com.nttdata.transaction.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.transaction.model.TransaccionDepositoRequest;
import com.nttdata.transaction.model.TransaccionDepositoResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TransaccionDepositoJsonReader {

    public static TransaccionDepositoRequest readTransaccionDepositoRequestFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), new TypeReference<TransaccionDepositoRequest>() {});
    }

    public static List<TransaccionDepositoResponse> readTransaccionDepositoResponseFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), new TypeReference<List<TransaccionDepositoResponse>>() {});
    }

}

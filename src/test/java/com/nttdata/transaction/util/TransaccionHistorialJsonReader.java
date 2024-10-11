package com.nttdata.transaction.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.nttdata.transaction.model.TransaccionHistorialResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TransaccionHistorialJsonReader {

    public static List<TransaccionHistorialResponse> readTransaccionHistorialResponseFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), new TypeReference<List<TransaccionHistorialResponse>>() {});
    }
}

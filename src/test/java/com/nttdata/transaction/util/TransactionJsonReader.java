package com.nttdata.transaction.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.nttdata.transaction.model.entity.dto.CuentaResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CuentaResponseJsonReader {
    public static CuentaResponse readCuentaResponseFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), new TypeReference<CuentaResponse>() {});
    }

}

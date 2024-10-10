package com.nttdata.transaction.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "transacciones")
public class Transaction {

    @Id
    private String id;

    private String tipo;

    private Double monto;

    private Long cuentaOrigenId;

    private Long cuentaDestinoId;

    private LocalDate fecha;
}

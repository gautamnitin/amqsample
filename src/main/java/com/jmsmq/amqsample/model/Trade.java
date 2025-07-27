package com.jmsmq.amqsample.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "Trade", description = "Trade message payload")
public class Trade {
    @Schema(description = "Trade ID", example = "T20250725-BOND01")
    private String id;

    @Schema(description = "Trade type", example = "BOND")
    private String type;

    @Schema(description = "Trade amount", example = "1000000.0")
    private Double amount;
}
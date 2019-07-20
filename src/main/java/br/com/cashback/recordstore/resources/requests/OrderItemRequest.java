package br.com.cashback.recordstore.resources.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class OrderItemRequest {

    @NotNull(message = "The record id is required")
    @Positive(message = "The record id is required")
    @JsonProperty("record_id")
    private long recordId;

    public long getRecordId() {
        return recordId;
    }
}

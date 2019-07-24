package br.com.cashback.recordstore.resources.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class OrderItemRequest {

    @NotNull(message = "The record id is required")
    @JsonProperty("record_id")
    private String recordId;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}

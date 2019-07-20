package br.com.cashback.recordstore.resources.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class OrderRequest {

    @NotNull(message = "The record id is required")
    @Positive(message = "The record id is required")
    @JsonProperty("record_id")
    private int recordId;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
}

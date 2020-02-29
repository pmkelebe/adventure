package com.pmkelebe.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "gross",
        "vat"
})
@Data
public class Total {

    @JsonProperty("gross")
    private Double gross = 0.0;
    @JsonProperty("vat")
    private Double vat = 0.0;

}
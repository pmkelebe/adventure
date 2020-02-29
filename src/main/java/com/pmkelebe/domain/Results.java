package com.pmkelebe.domain;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "results",
        "total"
})
@Data
public class Results {

    @JsonProperty("results")
    private List<Item> items;
    @JsonProperty("total")
    private Total total;

}

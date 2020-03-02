package com.pmkelebe.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "title",
        "kcal_per_100g",
        "unit_price",
        "description"
})
@Data
public class Item {

    @JsonProperty("title")
    private String title;
    @JsonProperty("kcal_per_100g")
    private Integer kcalPer100g;
    @JsonProperty("unit_price")
    private Double unitPrice;
    @JsonProperty("description")
    private String description;
}

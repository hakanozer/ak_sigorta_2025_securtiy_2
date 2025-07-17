package com.works.entities.dtos;

import jakarta.validation.constraints.*;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.works.entities.Product}
 */
@Value
public class ProductSaveDto implements Serializable {
    @NotNull
    @Size(min = 2, max = 100)
    @NotEmpty
    String title;
    @NotNull
    @Min(2)
    @Max(9000000)
    Integer price;
}
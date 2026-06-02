package com.independence.prototype.entity.DTO.request;

import jakarta.validation.constraints.Size;

public record ViewRequest(

        @Size(min = 3 , max = 100)
        String title
) {
}

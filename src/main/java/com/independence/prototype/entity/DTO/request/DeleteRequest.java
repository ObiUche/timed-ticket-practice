package com.independence.prototype.entity.DTO.request;

import jakarta.validation.constraints.Size;

public record DeleteRequest(
        @Size(min = 3, max = 140, message = "Title must be between 3 - 140 characters.")
        String title
) {
}

package com.example.FinalProject.Request.ProductHistoryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

public record GetProductHistory2(@Schema(example = "YYYY-MM-DD")@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must follow the format YYYY-MM-DD") String Date, String userId) {
}

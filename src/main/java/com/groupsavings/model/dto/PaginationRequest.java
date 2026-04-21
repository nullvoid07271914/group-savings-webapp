package com.groupsavings.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequest {

    private int page = 0;      // default page 0
    private int size = 10;     // default size 10
    private String sortBy = "id";
    private String sortDir = "ASC";
}

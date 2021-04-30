package com.example.demo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class OrderRequestDto implements Serializable {
    private static final long serialVersionUID = 2679072538669083366L;
    private Set<Long> itemIds;
    private Long customerId;
}

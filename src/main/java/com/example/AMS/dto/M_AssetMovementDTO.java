// M_AssetMovementDTO.java
package com.example.AMS.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class M_AssetMovementDTO {
    @NotBlank(message = "Asset is required")
    private String assetId;
    
    @NotBlank(message = "Location is required")
    private String locationId;
    
    private String roomId;
    
    @NotNull(message = "Date is required")
    private Date allocationDate;
    
    private String notes;
}
// com.example.AMS.dto.L_UserHistoryDto
package com.example.AMS.dto;

import java.util.Date;

public interface L_UserHistoryDto {
    String getUserName();
    String getJobRole();
    String getUserDescription();
    String getAssetId();
    String getAssetName();
    String getAssetSerialNumber(); // Changed from getAssetBrand to match query
    String getAssetModel();
    String getDepartmentName();
    String getRoomName();
    Date getStartDate();
    Date getEndDate();
}


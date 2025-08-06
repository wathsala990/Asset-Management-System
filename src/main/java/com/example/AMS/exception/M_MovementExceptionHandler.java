// M_MovementExceptionHandler.java
package com.example.AMS.exception;

import com.example.AMS.controller.admin.M_AssetMovementController;
import com.example.AMS.controller.admin.M_LocationController;
import com.example.AMS.controller.admin.M_RoomController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {
    M_LocationController.class,
    M_RoomController.class,
    M_AssetMovementController.class
})
public class M_MovementExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "Movement/admin/Movement";
    }
}
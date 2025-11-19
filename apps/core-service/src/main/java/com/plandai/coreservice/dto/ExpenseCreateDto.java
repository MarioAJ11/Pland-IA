package com.plandai.coreservice.dto;

import com.plandai.coreservice.model.Expense;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ExpenseCreateDto {
    
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal amount;
    
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String description;
    
    @NotNull(message = "La fecha del gasto es obligatoria")
    private LocalDate expenseDate;
    
    private Expense.PaymentMethod paymentMethod;
    
    private UUID categoryId;
    
    @NotNull(message = "El ID de usuario es obligatorio")
    private UUID userId;
    
    private Boolean isRecurring = false;
    
    private Expense.RecurrencePeriod recurrencePeriod;
}

package com.ct.squad.spend.sense.transactions.dto.request;

import com.ct.squad.spend.sense.transactions.models.enums.TransactionTypeShort;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

/**
 * DTO for {@link com.ct.squad.spend.sense.transactions.models.Transaction}
 */
@Data
public class CreateTransactionDto {
    @NotNull(message = "Money Account Name is required.")
    @NotBlank(message = "Money Account Name is required.")
    private String moneyAccountName;
    @NotNull(message = "Currency ID is required.")
    private Integer currencyId;
    @NotNull(message = "Currency Name is required.")
    @NotBlank(message = "Currency Name is required.")
    private String currencyName;
    @NotNull(message = "Account Type is required.")
    @NotBlank(message = "Account Type is required.")
    private String accountType;
    @NotNull(message = "Product is required.")
    @NotBlank(message = "Product is required.")
    private String product;
    @NotNull(message = "Customer Name is required.")
    private String customerName;
    @NotNull(message = "Transaction ID is required.")
    private Long transactionId;
    @NotNull(message = "Transaction Type ID is required.")
    private Integer transactionTypeId;
    @NotNull(message = "Transaction Type Short is required")
    private TransactionTypeShort transactionTypeShort;
    @NotNull(message = "Transaction Type Name is required.")
    @NotBlank(message = "Transaction Type Name is required.")
    private String transactionTypeName;
    @NotNull(message = "Booking Type Short is required.")
    @NotBlank(message = "Booking Type Short is required.")
    private String bookingTypeShort;
    @NotNull(message = "Booking Type Name is required.")
    @NotBlank(message = "Booking Type Name is required.")
    private String bookingTypeName;
    @NotNull(message = "Value Date is required.")
    private Date valueDate;
    @NotNull(message = "Transaction Date is required.")
    private Date transactionDate;
    @NotNull(message = "Direction is required.")
    private Integer direction;
    @NotNull(message = "Amount is required.")
    private Double amount;
    @NotNull(message = "Transaction Currency ID is required.")
    private Integer transactionCurrencyId;
    @NotNull(message = "Transaction Currency name is required.")
    private String transactionCurrencyName;
    private String creditorShortText;
    private String creditorText;
    private String debtorShortText;
    private String debtorText;
    private String pointOfSale;
    private Integer acquirerCountryId;
    private String acquirerCountryName;
    private String cardId;
    private String creditorAccountText;
    private String creditorIBAN;
    private String creditorAddress;
    private String creditorReferenceNumber;
    private String creditorInfo;
}
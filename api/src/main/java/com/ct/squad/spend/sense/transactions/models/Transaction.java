package com.ct.squad.spend.sense.transactions.models;

import com.ct.squad.spend.sense.transactions.models.enums.Category;
import com.ct.squad.spend.sense.transactions.models.enums.Subcategory;
import com.ct.squad.spend.sense.transactions.models.enums.TransactionTypeShort;
import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Subcategory subcategory;

    private String moneyAccountName;

    @Description("Currency identifier code (ISO code, e.g. '978' for EUR)")
    private Integer currencyId;

    @Description("Currency name (e.g. 'EUR', 'CHF')")
    private String currencyName;

    private String accountType;

    @Description("Product linked to the account/transaction")
    private String product;

    private String customerName;

    private Long transactionId;

    private Integer transactionTypeId;

    @Enumerated(EnumType.STRING)
    private TransactionTypeShort transactionTypeShort;

    @Description("Full descriptive name of the transaction")
    private String transactionTypeName;

    @Description("Short code for booking type")
    private String bookingTypeShort;

    @Description("Full descriptive name of the booking type")
    private String bookingTypeName;

    @Description("The date when amount is effective for interest/settlement")
    private Date valueDate;

    private Date transactionDate;

    @Description("Transaction direction (credit/debit; inflow/outflow)")
    private Integer direction;

    private Double amount;

    private Integer transactionCurrencyId;

    @Description("Name of the transaction currency (e.g. CHF, EUR)")
    private String transactionCurrencyName;

    @Description("Short text/identifier of the creditor (who receives money)")
    private String creditorShortText;

    @Description("Full description of creditor")
    private String creditorText;

    @Description("Short text/identifier of the debtor (payer)")
    private String debtorShortText;

    @Description("Full description of debtor")
    private String debtorText;

    @Description("Where the payment was made")
    private String pointOfSale;

    private Integer acquirerCountryId;

    private String acquirerCountryName;

    @Description("The id of the card. String because it will be written as 2322XXX")
    private String cardId;

    private String creditorAccountText;

    private String creditorIBAN;

    private String creditorAddress;

    private String creditorReferenceNumber;

    private String creditorInfo;
}

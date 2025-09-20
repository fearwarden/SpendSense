package com.ct.squad.spend.sense.transactions.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionTypeShort {
    CARDTRX(44, "Kartentransaktion"),
    COP(47, "Kass"),
    CORD(35, "Sammelauftrag"),
    INPAY(30, "Zahlungseingang"),
    INTR(16, "Interest"),
    PAY(6, "Zahlungsverkehr"),
    XFERMON(8, "Geldübertrag");

    private final Integer transactionTypeId;
    private final String transactionTypeName;
}

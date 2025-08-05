package com.saida.register_customer.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessage {

    public static final String CUSTOMER_NOT_FOUND = "error.customerNotFound";
    public static final String DUPLICATE_ACCOUNT_NUMBER = "error.duplicateAccountNumber";
    public static final String ACCOUNT_NOT_FOUND = "error.accountNotFound";
    public static final String INVALID_AMOUNT = "error.invalidAmount";
    public static final String INVALID_REFUND_AMOUNT = "error.invalidRefundAmount";
    public static final String INVALID_CURRENCY = "error.invalidCurrency";
    public static final String INVALID_CUSTOMER = "error.invalidCustomer";
    public static final String INVALID_ACCOUNT = "error.invalidAccount";
    public static final String INVALID_ENUM_VALUE = "error.invalidEnumValue";
    public static final String MISMATCH_CURRENCY = "error.mismatchCurrency";
    public static final String INVALID_TRANSFER_TYPE = "error.invalidTransferType";
    public static final String TRANSFER_NOT_FOUND = "error.transferNotFound";
    public static final String AUTHORISATION_NULL = "error.authorisationIsNull";
    public static final String EXPIRED_TOKEN = "error.expiredToken";
    public static final String INVALID_TOKEN = "error.invalidToken";
    public static final String ACCESS_DENIED = "error.accessDenied";
}

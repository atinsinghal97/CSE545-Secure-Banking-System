package constants;

import java.math.BigDecimal;

public final class Constants {

    private Constants() {
            // restrict instantiation
    }

    public static final String TIER1 = "tier1";
    public static final String TIER2 = "tier2";
    public static final String TIER3 = "tier3";
    public static final String ADMIN = "admin";
    public static final String INDIVIDUAL = "individual";
    public static final String CUSTOMER = "customer";
    public static final int DEFAULT_TIER1 = 9;
    public static final int DEFAULT_TIER2 = 3;
    public static final int DEFAULT_ADMIN = 4;
    public static final String TRANSFER = "transfer";
    public static final String DEBIT = "debit";
    public static final String CREDIT = "credit";
    public static final String CHEQUE = "cc";
    public static final BigDecimal THRESHOLD_AMOUNT = BigDecimal.valueOf(1000);






}
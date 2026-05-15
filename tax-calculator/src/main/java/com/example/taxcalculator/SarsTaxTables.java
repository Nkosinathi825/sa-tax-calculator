package com.example.taxcalculator;

import java.math.BigDecimal;
import java.util.List;

class SarsTaxTables {

    static final List<TaxBracket> BRACKETS_2024_2025 = List.of(
            new TaxBracket(1,          237_100,          new BigDecimal("0"),       new BigDecimal("0.18")),
            new TaxBracket(237_101,    370_500,          new BigDecimal("42678"),   new BigDecimal("0.26")),
            new TaxBracket(370_501,    512_800,          new BigDecimal("77362"),   new BigDecimal("0.31")),
            new TaxBracket(512_801,    673_000,          new BigDecimal("121475"),  new BigDecimal("0.36")),
            new TaxBracket(673_001,    857_900,          new BigDecimal("179147"),  new BigDecimal("0.39")),
            new TaxBracket(857_901,    1_817_000,        new BigDecimal("251258"),  new BigDecimal("0.41")),
            new TaxBracket(1_817_001,  Long.MAX_VALUE,   new BigDecimal("644489"),  new BigDecimal("0.45"))
    );

    static final BigDecimal REBATE_PRIMARY   = new BigDecimal("17235");
    static final BigDecimal REBATE_SECONDARY = new BigDecimal("9444");
    static final BigDecimal REBATE_TERTIARY  = new BigDecimal("3145");

    static final BigDecimal THRESHOLD_BELOW_65    = new BigDecimal("95750");
    static final BigDecimal THRESHOLD_65_TO_74    = new BigDecimal("148217");
    static final BigDecimal THRESHOLD_75_PLUS     = new BigDecimal("165689");

    static final BigDecimal UIF_MONTHLY_REMUNERATION_CAP = new BigDecimal("17712");
    static final BigDecimal UIF_RATE                     = new BigDecimal("0.01");

    static final BigDecimal MEDICAL_CREDIT_MAIN_MEMBER          = new BigDecimal("364");
    static final BigDecimal MEDICAL_CREDIT_FIRST_DEPENDANT      = new BigDecimal("364");
    static final BigDecimal MEDICAL_CREDIT_ADDITIONAL_DEPENDANT = new BigDecimal("246");

    private SarsTaxTables() {}
}

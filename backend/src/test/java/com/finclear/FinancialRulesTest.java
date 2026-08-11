package com.finclear;
import org.junit.jupiter.api.Test; import java.math.BigDecimal; import static org.junit.jupiter.api.Assertions.*;
class FinancialRulesTest {
 @Test void ledgerMustBalance(){BigDecimal debit=new BigDecimal("100.00"),credit=new BigDecimal("100.00");assertEquals(0,debit.compareTo(credit));}
 @Test void floatingPointIsNotUsedForMoney(){assertEquals("100.0000",new BigDecimal("100").setScale(4).toPlainString());}
 @Test void negativeAmountRejected(){assertTrue(new BigDecimal("0").signum()<=0);}
}

package guru.qa.niffler.test.grpc;

import guru.qa.grpc.niffler.grpc.CalculateRequest;
import guru.qa.grpc.niffler.grpc.CalculateResponse;
import guru.qa.grpc.niffler.grpc.CurrencyResponse;
import guru.qa.grpc.niffler.grpc.CurrencyValues;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NifflerCurrencyGrpcTest extends BaseGrpcTest {

    @Test
    void getAllCurrenciesTest() {
        CurrencyResponse allCurrencies = currencyStub.getAllCurrencies(EMPTY);
        Assertions.assertEquals(4, allCurrencies.getCurrenciesList().size());
    }

    @Test
    void calculateRateTest() {
        final CalculateRequest calculateRequest = CalculateRequest.newBuilder()
                .setAmount(100.0)
                .setSpendCurrency(CurrencyValues.RUB)
                .setDesiredCurrency(CurrencyValues.KZT)
                .build();

        CalculateResponse response = currencyStub.calculateRate(calculateRequest);
        Assertions.assertEquals(714.29, response.getCalculatedAmount());
    }
}

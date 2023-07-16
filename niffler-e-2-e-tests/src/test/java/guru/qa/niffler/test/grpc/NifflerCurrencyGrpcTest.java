package guru.qa.niffler.test.grpc;

import guru.qa.grpc.niffler.grpc.CalculateRequest;
import guru.qa.grpc.niffler.grpc.CalculateResponse;
import guru.qa.grpc.niffler.grpc.CurrencyResponse;
import guru.qa.grpc.niffler.grpc.CurrencyValues;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static guru.qa.grpc.niffler.grpc.CurrencyValues.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

public class NifflerCurrencyGrpcTest extends BaseGrpcTest {

    @Test
    void getAllCurrenciesTest() {
        CurrencyResponse allCurrencies = currencyStub.getAllCurrencies(EMPTY);
        assertThat(allCurrencies.getCurrenciesList().size())
                .isEqualTo(4);
        assertThat(allCurrencies.getCurrenciesList().get(0).getCurrency())
                .isEqualTo(RUB);
        assertThat(allCurrencies.getCurrenciesList().get(1).getCurrency())
                .isEqualTo(KZT);
        assertThat(allCurrencies.getCurrenciesList().get(2).getCurrency())
                .isEqualTo(EUR);
        assertThat(allCurrencies.getCurrenciesList().get(3).getCurrency())
                .isEqualTo(USD);

    }

    static Stream<Arguments> testCurrencyData() {
        return Stream.of(
                of(150.00, RUB, KZT, 1071.43),
                of(34.00, USD, EUR, 31.48),
                of(150.00, RUB, RUB, 150.00),
                of(0.00, KZT, RUB, 0.00)
        );
    }

    @MethodSource("testCurrencyData")
    @ParameterizedTest
    void calculateRateTest(double amount, CurrencyValues spendCurrency, CurrencyValues desiredCurrency, double expectedAmount) {
        final CalculateRequest cr = CalculateRequest.newBuilder()
                .setAmount(amount)
                .setSpendCurrency(spendCurrency)
                .setDesiredCurrency(desiredCurrency)
                .build();

        CalculateResponse response = currencyStub.calculateRate(cr);
        assertThat(response.getCalculatedAmount())
                .isEqualTo(expectedAmount);
    }
}

package guru.qa.niffler.service;

import com.google.protobuf.Empty;
import guru.qa.grpc.niffler.grpc.CurrencyResponse;
import guru.qa.grpc.niffler.grpc.CurrencyValues;
import guru.qa.niffler.data.CurrencyEntity;
import guru.qa.niffler.data.repository.CurrencyRepository;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static guru.qa.niffler.data.CurrencyValues.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GrpcCurrencyServiceTest {
    GrpcCurrencyService grpcCurrencyService;
    List<CurrencyEntity> testCurrencies;

    @BeforeEach
    void setUp(@Mock CurrencyRepository currencyRepository) {
        CurrencyEntity rub = new CurrencyEntity();
        rub.setCurrency(RUB);
        rub.setCurrencyRate(0.015);
        CurrencyEntity usd = new CurrencyEntity();
        usd.setCurrency(USD);
        usd.setCurrencyRate(1.0);
        CurrencyEntity eur = new CurrencyEntity();
        eur.setCurrency(EUR);
        eur.setCurrencyRate(1.08);
        CurrencyEntity kzt = new CurrencyEntity();
        kzt.setCurrency(KZT);
        kzt.setCurrencyRate(0.0021);

        testCurrencies = List.of(rub, kzt, eur, usd);

        lenient()
                .when(currencyRepository.findAll())
                .thenReturn(testCurrencies);

        grpcCurrencyService = new GrpcCurrencyService(currencyRepository);
    }


    static Stream<Arguments> convertSpendTo() {
        return Stream.of(
                Arguments.of(150.00, guru.qa.grpc.niffler.grpc.CurrencyValues.RUB, guru.qa.grpc.niffler.grpc.CurrencyValues.KZT, 1071.43),
                Arguments.of(34.00, guru.qa.grpc.niffler.grpc.CurrencyValues.USD, guru.qa.grpc.niffler.grpc.CurrencyValues.EUR, 31.48),
                Arguments.of(150.00, guru.qa.grpc.niffler.grpc.CurrencyValues.RUB, guru.qa.grpc.niffler.grpc.CurrencyValues.RUB, 150.00),
                Arguments.of(0.00, guru.qa.grpc.niffler.grpc.CurrencyValues.KZT, guru.qa.grpc.niffler.grpc.CurrencyValues.RUB, 0.00)
        );
    }

    @MethodSource
    @ParameterizedTest
    void convertSpendTo(double spend,
                        guru.qa.grpc.niffler.grpc.CurrencyValues spendCurrency,
                        guru.qa.grpc.niffler.grpc.CurrencyValues desiredCurrency,
                        double expectedResult) {

        BigDecimal result = grpcCurrencyService.convertSpendTo(spend, spendCurrency,
                desiredCurrency, testCurrencies);

        Assertions.assertEquals(expectedResult, result.doubleValue());
    }

    @Test
    void getAllCurrencies() {
        StreamObserver<CurrencyResponse> responseStreamObserver = Mockito.mock(StreamObserver.class);
        Empty defaultInstanceRequest = Empty.getDefaultInstance();
        grpcCurrencyService.getAllCurrencies(defaultInstanceRequest, responseStreamObserver);
        verify(responseStreamObserver, times(1)).onNext(any());
        verify(responseStreamObserver, times(1)).onCompleted();
    }

    @Test
    void calculateRate() {
        StreamObserver<CurrencyResponse> responseStreamObserver = mock(StreamObserver.class);
        Empty defaultInstanceRequest = Empty.getDefaultInstance();
        grpcCurrencyService.getAllCurrencies(defaultInstanceRequest, responseStreamObserver);
        verify(responseStreamObserver, times(1)).onNext(any());
        verify(responseStreamObserver, times(1)).onCompleted();
    }

    // Negative values
    static Stream<Arguments> courseForCurrency() {
        return Stream.of(
                Arguments.of(guru.qa.grpc.niffler.grpc.CurrencyValues.RUB, 7.77),
                Arguments.of(guru.qa.grpc.niffler.grpc.CurrencyValues.USD, 6.666),
                Arguments.of(guru.qa.grpc.niffler.grpc.CurrencyValues.EUR, 0.999),
                Arguments.of(guru.qa.grpc.niffler.grpc.CurrencyValues.KZT, 2.345)
        );
    }
    @MethodSource
    @ParameterizedTest
    void courseForCurrency(CurrencyValues currencyValues, double expectedResult) {
        BigDecimal actualCourseForCurrency = grpcCurrencyService.courseForCurrency(currencyValues, testCurrencies);
        Assertions.assertEquals(expectedResult, actualCourseForCurrency.doubleValue());
    }
}
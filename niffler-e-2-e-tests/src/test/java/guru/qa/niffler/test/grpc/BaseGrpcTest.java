package guru.qa.niffler.test.grpc;

import com.google.protobuf.Empty;
import guru.qa.grpc.niffler.grpc.NifflerCategoryServiceGrpc;
import guru.qa.grpc.niffler.grpc.NifflerCategoryServiceGrpc.NifflerCategoryServiceBlockingStub;
import guru.qa.grpc.niffler.grpc.NifflerCurrencyServiceGrpc;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.GrpcTest;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.qameta.allure.grpc.AllureGrpc;

import static guru.qa.grpc.niffler.grpc.NifflerCategoryServiceGrpc.*;
import static guru.qa.grpc.niffler.grpc.NifflerCurrencyServiceGrpc.NifflerCurrencyServiceBlockingStub;

@GrpcTest
public class BaseGrpcTest {

    protected static final Config CFG = Config.getConfig();

    protected static final Empty EMPTY = Empty.getDefaultInstance();
    private static Channel currencyChannel;
    private static Channel spendChannel;

    // Задаем настройки канала
    static {
        currencyChannel = ManagedChannelBuilder
                .forAddress(CFG.getCurrencyGrpcAddress(), CFG.getCurrencyGrpcPort())
                .intercept(new AllureGrpc())
                .usePlaintext()
                .build();

        spendChannel = ManagedChannelBuilder
                .forAddress(CFG.getSpendGrpcAddress(), CFG.getSpendGrpcPort())
                .intercept(new AllureGrpc())
                .usePlaintext()
                .build();
    }

    // Stub позволяет делать запросы
    protected final NifflerCurrencyServiceBlockingStub currencyStub =
            NifflerCurrencyServiceGrpc.newBlockingStub(currencyChannel);

    protected final NifflerCategoryServiceBlockingStub categoryStub =
            newBlockingStub(spendChannel);
}

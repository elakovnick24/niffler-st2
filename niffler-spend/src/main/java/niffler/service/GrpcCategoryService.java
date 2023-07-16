package niffler.service;

import com.google.protobuf.Empty;
import guru.qa.grpc.niffler.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import niffler.data.CategoryEntity;
import niffler.data.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class GrpcCategoryService extends NifflerCategoryServiceGrpc.NifflerCategoryServiceImplBase {

    private final CategoryRepository categoryRepository;
    private final GrpcCurrencyClient grpcCurrencyClient;

    public GrpcCategoryService(CategoryRepository categoryRepository,
                               GrpcCurrencyClient grpcCurrencyClient) {
        this.categoryRepository = categoryRepository;
        this.grpcCurrencyClient = grpcCurrencyClient;
    }

    @Override
    public void getAllCategories(Empty request,
                                 StreamObserver<guru.qa.grpc.niffler.grpc.CategoriesResponse> responseObserver) {
        List<CategoryEntity> all = categoryRepository.findAll();

        CategoriesResponse response = CategoriesResponse.newBuilder()
                .addAllCategories(all.stream()
                        .map(categoryEntity -> Category.newBuilder()
                                .setCategory(categoryEntity.getCategory())
                                .setUsername(categoryEntity.getUsername())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addCategory(CategoryRequest request, StreamObserver<CategoryResponse> responseObserver) {
        CategoryEntity categoryEntity = new CategoryEntity();
        CategoryResponse categoryResponse;
        final String category = request.getCategory();
        final String username = request.getUsername();

        categoryEntity.setCategory(category);
        categoryEntity.setUsername(username);
        categoryRepository.save(categoryEntity);

        categoryResponse = CategoryResponse.newBuilder()
                .setId(String.valueOf(categoryEntity.getId()))
                .setCategory(request.getCategory())
                .setUsername(request.getUsername())
                .build();

        responseObserver.onNext(categoryResponse);
        responseObserver.onCompleted();
    }
}

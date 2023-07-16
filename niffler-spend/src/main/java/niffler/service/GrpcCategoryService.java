package niffler.service;

import guru.qa.grpc.niffler.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import niffler.data.CategoryEntity;
import niffler.data.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class GrpcCategoryService extends NifflerCategoryServiceGrpc.NifflerCategoryServiceImplBase {

    private final CategoryRepository categoryRepository;
    private final GrpcCurrencyClient grpcCurrencyClient;
    private static final int MAX_CATEGORIES_SIZE = 7;

    public GrpcCategoryService(CategoryRepository categoryRepository,
                               GrpcCurrencyClient grpcCurrencyClient) {
        this.categoryRepository = categoryRepository;
        this.grpcCurrencyClient = grpcCurrencyClient;
    }

    @Override
    public void getAllCategories(CategoriesRequest request, StreamObserver<CategoriesResponse> responseObserver) {
        List<CategoryEntity> all = categoryRepository.findAllByUsername(request.getUsername());
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
        if (categoryRepository.findAllByUsername(request.getUsername()).size() > MAX_CATEGORIES_SIZE) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    "Can`t add over than 7 categories for user: '" + request.getUsername());
        }
        CategoryEntity categoryEntity = new CategoryEntity();
        CategoryResponse categoryResponse;
        final String category = request.getCategory();
        final String username = request.getUsername();

        categoryEntity.setCategory(category);
        categoryEntity.setUsername(username);
        categoryRepository.save(categoryEntity);

        categoryResponse = CategoryResponse.newBuilder()
                .setUsername(categoryEntity.getUsername())
                .setCategory(Category.newBuilder()
                        .setCategory(categoryEntity.getCategory())
                        .setId(categoryEntity.getId().toString()).build())
                .setUsername(categoryEntity.getUsername())
                .build();

        responseObserver.onNext(categoryResponse);
        responseObserver.onCompleted();
    }
}

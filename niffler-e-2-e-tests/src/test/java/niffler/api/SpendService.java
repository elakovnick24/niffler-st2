package niffler.api;

import niffler.model.CategoryJson;
import niffler.model.SpendJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SpendService {

    @POST("/addSpend")
    Call<SpendJson> addSpend(@Body SpendJson spend);

    @GET("/categories")
    Call<CategoryJson> getCategories(@Query("username") String username);

    @POST("/category")
    Call<CategoryJson> addCategory (@Body CategoryJson category);

}

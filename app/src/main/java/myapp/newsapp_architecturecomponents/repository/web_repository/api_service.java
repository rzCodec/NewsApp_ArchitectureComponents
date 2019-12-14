package myapp.newsapp_architecturecomponents.repository.web_repository;

import myapp.newsapp_architecturecomponents.repository.BusinessNews;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface api_service {
    @GET("/get_article_content") //This is part of the get request url
    Call<List<BusinessNews>> get_article_content();
}

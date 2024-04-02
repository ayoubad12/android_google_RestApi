package ma.enset.tp3_booksrestapi.service;

import ma.enset.tp3_booksrestapi.model.GoogleBookResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookServiceApi{
        @GET("v1/volumes") //retrofit va envoyer une requete vers le hostname. Et nous allons ajouter: search/users
        public Call<GoogleBookResponse> searchBooks(@Query("q") String query); //this
}

package ma.enset.tp3_booksrestapi.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GoogleBookResponse {
    public String kind;
    @SerializedName("totalItems")
    public long nbrBooks ;
    @SerializedName("items")
    public List<Book> books = new ArrayList<>();
}

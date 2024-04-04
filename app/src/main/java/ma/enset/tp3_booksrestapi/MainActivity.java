package ma.enset.tp3_booksrestapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ma.enset.tp3_booksrestapi.adapter.BookListViewAdapter;
import ma.enset.tp3_booksrestapi.model.Book;
import ma.enset.tp3_booksrestapi.model.GoogleBookResponse;
import ma.enset.tp3_booksrestapi.service.BookServiceApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    List<Book> data =  new ArrayList<>();
    public static final String BOOK_CLICKED = "book clicked";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //pour permetre l'application d'afficher les images sous forme d'URL
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setTitle("Main");

        //defined the UI elements
        final EditText editTextQuery = findViewById(R.id.editTextQuery);
        Button buttonSearch = findViewById(R.id.searchButton);
        ListView listViewUsers = findViewById(R.id.listViewBooks);



        //setting the adapter
        final BookListViewAdapter bookListViewAdapter = new BookListViewAdapter(this, R.layout.books_list_layout, data);
        listViewUsers.setAdapter(bookListViewAdapter);

        //set up retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/books/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //create a click event listener on search button
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editTextQuery.getText().toString();
                Log.i("query", query);
                BookServiceApi bookServiceApi = retrofit.create(BookServiceApi.class);
                Call<GoogleBookResponse> callGoogleBook = bookServiceApi.searchBooks(query);

                callGoogleBook.enqueue(new Callback<GoogleBookResponse>() {
                    @Override
                    public void onResponse(Call<GoogleBookResponse> call, Response<GoogleBookResponse> response) {
                        Log.i("url", call.request().url().toString());
                        if(!response.isSuccessful()){
                            Log.i("indo", String.valueOf(response.code()));
                            return ;
                        }
                        GoogleBookResponse googleBookResponse = response.body();
                        Log.i("right before for loop", "ok");
                        Log.i("nbr books", String.valueOf(googleBookResponse.nbrBooks));

                        if(googleBookResponse.books!=null){
                            for(Book book:googleBookResponse.books){
                                data.add(book);
                            }
                        }else{
                            Log.e("error", "Books list is null");
                        }
                        bookListViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<GoogleBookResponse> call, Throwable t) {
                        Log.e("error", "Error");
                    }
                });
            }
        });
        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = data.get(position) ;
                Intent intent = new Intent(getApplicationContext(), BookActivity.class);
                intent.putExtra(BOOK_CLICKED, book);
                startActivity(intent);
            }
        });
    }
}
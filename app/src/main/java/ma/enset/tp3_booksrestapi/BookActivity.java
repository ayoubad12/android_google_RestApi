package ma.enset.tp3_booksrestapi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.net.URL;

import ma.enset.tp3_booksrestapi.model.Book;


public class BookActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_layout);
        setTitle("book description");


        //retrieve the parameter "USER_LOGIN_PARAM" that was passed by the previous Intent
        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra(MainActivity.BOOK_CLICKED);
        Log.i("book id", book.id); //debug to see if the login is assigned
        Log.i("book title", book.volumeInfo.title);

        //Get UI element
        ImageView imageViewBook = findViewById(R.id.imageViewBook);
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewAuthors = findViewById(R.id.textViewAuthors);
        TextView textViewPublishedDate = findViewById(R.id.textViewPublishedDate);
        TextView textViewDescription = findViewById(R.id.textViewDescription);


        textViewTitle.setText(book.volumeInfo.title);

        String allAuthors = null;
        for (String author: book.volumeInfo.authors)
            allAuthors += author+", " ;

        textViewAuthors.setText("Authors: "+allAuthors);
        textViewPublishedDate.setText("Published: "+book.volumeInfo.publishedDate);

        //display profile image
        try {
            URL url = new URL(book.volumeInfo.imageLinks.thumbnail);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
            imageViewBook.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        textViewDescription.setText(book.volumeInfo.description);

    }
}

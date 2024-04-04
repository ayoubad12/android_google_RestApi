package ma.enset.tp3_booksrestapi.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.net.URL;
import java.util.List;

//import de.hdodenhof.circleimageview.CircleImageView;?
import ma.enset.tp3_booksrestapi.R;
import ma.enset.tp3_booksrestapi.model.Book;

public class BookListViewAdapter extends ArrayAdapter<Book> {
    private List<Book> books;
    private int resource;
    public BookListViewAdapter(@NonNull Context context, int resource, List<Book> data){
        super(context, resource, data);
        this.books=data;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;
        if(listViewItem==null){
            listViewItem = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }
//        CircleImageView imageViewBook = listViewItem.findViewById(R.id.imageViewBook);
        ImageView imageViewBook = listViewItem.findViewById(R.id.imageViewBook);
        TextView textViewTitle = listViewItem.findViewById(R.id.textViewTitle);
        TextView textViewPublishedDate = listViewItem.findViewById(R.id.textViewPublishedDate);

        textViewTitle.setText(getItem(position).volumeInfo.title); //since we already passed data to the parent in our constructor. we don't need to use: users.get(position).score ; we can use instead: getItem(position).login ;
        textViewPublishedDate.setText(String.valueOf(getItem(position).volumeInfo.publishedDate));

        //display profile image
        try {
            URL url = new URL(getItem(position).volumeInfo.imageLinks.smallThumbnail);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
            imageViewBook.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listViewItem;
    }
}

package ma.enset.tp3_booksrestapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VolumeInfo implements Serializable {
    public String title;
    public List<String> authors = new ArrayList<>();
    public String publisher;
    public String publishedDate;
    public String description;
    public ImageLinks imageLinks;
}

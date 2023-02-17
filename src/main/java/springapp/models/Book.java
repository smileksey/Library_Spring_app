package springapp.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Book {
    private int id;
    @NotNull(message = "Это поле не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно состоять миниму из 2 и максимум из 50 символов")
    private String title;
    @NotNull(message = "Это поле не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя автора должно состоять миниму из 2 и максимум из 50 символов")
    private String author;
    private int year;
    private int personId;

    public Book() {
    }

    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}

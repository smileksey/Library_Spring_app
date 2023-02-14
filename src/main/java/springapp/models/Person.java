package springapp.models;

import javax.validation.constraints.*;

public class Person {
    private int id;
    @NotNull(message = "Это поле не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно состоять миниму из 2 и максимум из 50 символов")
    private String name;
    @Min(value = 1920, message = "Введите корректный год рождения")
    private int yearOfBirth;

    public Person() {
    }

    public Person(int id, String name, int yearOfBirth) {
        this.id = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}

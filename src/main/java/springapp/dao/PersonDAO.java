package springapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springapp.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        //BeanPropertyRowMapper - реализация интерфейса RowMapper из библиотеки спринга, которая преобразует
        //строки из таблицы в объекты Person
        return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
    }

    //new Object[]{id} - массив со значениями, которые подставляются вместо "?" в SQL-запрос
    //в JDBC Templ для формирования SQL запроса используется PreparedStatement
    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public Optional<Person> show(String name, int yearOfBirth) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE name=? AND year_of_birth=?",
                                    new Object[]{name, yearOfBirth}, new PersonMapper())
                .stream().findAny();
    }

    //В аргументах сразу после запроса перечисляем (в виде varargs) значения, которые подставятся вместо "?"
    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person (name, year_of_birth) VALUES (?, ?)",
                                person.getName(), person.getYearOfBirth());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, year_of_birth=? WHERE id=?",
                                updatedPerson.getName(), updatedPerson.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}

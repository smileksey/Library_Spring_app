package springapp.dao;

import org.springframework.jdbc.core.RowMapper;
import springapp.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {

    //В этом методе берем очередную строку из ResultSet и переводим ее в объект Person
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        Person person = new Person();

        person.setId(resultSet.getInt("id"));
        person.setName(resultSet.getString("name"));
        person.setYearOfBirth(resultSet.getInt("year_of_birth"));

        return person;
    }
}

package springapp.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springapp.models.Book;
import springapp.models.Person;
import springapp.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);


        if (foundPerson.isPresent()) {
            Person person = foundPerson.get();
            Hibernate.initialize(person.getBooks());

            Date currentDate = new Date();

            for(Book b : person.getBooks()) {
                if (b.getDateOfBorrowing() != null) {

                    long diffInMillis = Math.abs(currentDate.getTime() - b.getDateOfBorrowing().getTime());
                    long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

                    if (diffInDays > 10)
                        b.setExpired(true);
                }
            }

            return person;
        }
        return null;
    }


    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public boolean personIsPresent(String name, int yearOfBirth) {
        Optional<Person> foundPerson = peopleRepository.findPersonByNameAndYearOfBirth(
                name, yearOfBirth);

        return foundPerson.isPresent();
    }
}

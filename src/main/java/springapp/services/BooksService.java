package springapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springapp.models.Book;
import springapp.models.Person;
import springapp.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }


    public List<Book> findAll(Integer page, Integer itemsPerPage, boolean sortByYear) {
        if (page != null && itemsPerPage != null) {
            if (sortByYear) {
                return booksRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("year"))).getContent();
            } else {
                return booksRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
            }
        } else {
            if (sortByYear) {
                return booksRepository.findAll(Sort.by("year"));
            } else {
                return booksRepository.findAll();
            }
        }
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public Optional<Book> findOne(String title) {
        return booksRepository.findBookByTitle(title);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void release(int id) {
        Book book = findOne(id);
        book.setOwner(null);
        book.setDateOfBorrowing(null);
    }

    @Transactional
    public void assign(int bookId, Person person) {
        Book book = findOne(bookId);
        book.setOwner(person);
        book.setDateOfBorrowing(new Date());
    }

    public Book findStartingWith(String titleStartingWith) {
        return booksRepository.findBookByTitleStartingWith(titleStartingWith).orElse(null);
    }


}

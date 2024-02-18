package ru.solonchevTeam.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.solonchevTeam.book.domain.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> {
}

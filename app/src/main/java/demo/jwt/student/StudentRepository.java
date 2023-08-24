package demo.jwt.student;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRepository extends CrudRepository<StudentEntity, Long> {
    Optional<StudentEntity> findByEmail(String email);
}
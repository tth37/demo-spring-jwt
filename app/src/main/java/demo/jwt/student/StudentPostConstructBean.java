package demo.jwt.student;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentPostConstructBean {
    @Autowired
    private StudentRepository studentRepository;

    @PostConstruct
    public void init() {
        if (studentRepository.count() == 0) {
            StudentEntity n = new StudentEntity();
            n.setName("admin");
            n.setEmail("admin@localhost");
            n.setPassword("admin");
            n.addRole("SUPERADMIN");
            studentRepository.save(n);
        }
    }
}

package demo.jwt.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentUserDetailsService {
    @Autowired
    private StudentRepository studentRepository;

    public UserDetails loadUserDetailsByEmail(String email) {
        Optional<StudentEntity> res = studentRepository.findByEmail(email);
        if (res.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        StudentEntity student = res.get();

        return User.withUsername(student.getEmail())
                .password("not used")
                .roles(student.getRoles().toArray(new String[0]))
                .build();
    }
}
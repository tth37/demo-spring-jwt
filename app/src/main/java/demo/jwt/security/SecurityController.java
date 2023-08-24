package demo.jwt.security;

import demo.jwt.student.StudentEntity;
import demo.jwt.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/security")
public class SecurityController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/hello")
    public String helloPost(@RequestParam String name) {
        return "hello " + name;
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        System.out.println("Login: " + email + " " + password);
        Optional<StudentEntity> res = studentRepository.findByEmail(email);
        if (res.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login failed");
        }
        StudentEntity student = res.get();
        System.out.println("Student: " + student);
        if (!student.comparePassword(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login failed");
        }
        return jwtUtils.generateJwtToken(student.getEmail());
    }
}

package demo.jwt.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/hello")
    public @ResponseBody String hello() {
        return "hello";
    }

    // Get Student by Id
    @GetMapping("/getStudentById")
    public @ResponseBody StudentEntity getStudentById(@RequestParam Long id) {
        Optional<StudentEntity> res = studentRepository.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Not Found");
    }

    // Get Student by Email
    @GetMapping("/getStudentByEmail")
    public @ResponseBody StudentEntity getStudentByEmail(@RequestParam String email) {
        Optional<StudentEntity> res = studentRepository.findByEmail(email);
        if (res.isPresent()) {
            return res.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Not Found");
    }

    // Get All Students
    @GetMapping("/getAllStudents")
    public @ResponseBody Iterable<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }


    // Add Student
    @PostMapping("/addStudent")
    public @ResponseBody String addStudent(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        Optional<StudentEntity> res = studentRepository.findByEmail(email);
        if (res.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        StudentEntity n = new StudentEntity();
        n.setName(name);
        n.setEmail(email);
        n.setPassword(password);
        studentRepository.save(n);
        return "Saved";
    }

//    @GetMapping("/all")

}

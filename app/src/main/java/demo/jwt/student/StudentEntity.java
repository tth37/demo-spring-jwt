package demo.jwt.student;

import jakarta.persistence.*;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSON;

@Entity
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    /**
     * Encrypted using BCryptPasswordEncoder
     */
    private String password;

    /**
     * JSON format, String array of roles
     */
    private String roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    public boolean comparePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, this.password);
    }

    private List<String> getRolesJSON() {
        List<String> res = JSON.parseArray(roles, String.class);
        if (res == null) {
            return new ArrayList<>();
        }
        return res;
    }

    private void setRolesJSON(List<String> roles) {
        this.roles = JSON.toJSONString(roles);
    }

    public void addRole(String role) {
        List<String> roles = getRolesJSON();
        roles.add(role);
        setRolesJSON(roles);
    }

    public void removeRole(String role) {
        List<String> roles = getRolesJSON();
        roles.remove(role);
        setRolesJSON(roles);
    }

    public List<String> getRoles() {
        return getRolesJSON();
    }
}

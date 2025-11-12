package org.example.scheduler.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.scheduler.config.PasswordEncoder;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4, nullable = false)
    private String name;

    @Column(unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    public User(String name, String email, String password, PasswordEncoder passwordEncoder) {
        super();
        this.name = name;
        this.email = email;
        this.password = passwordEncoder.encode(password);
    }

    public void modify(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public boolean isValid(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password,this.password);
    }
}

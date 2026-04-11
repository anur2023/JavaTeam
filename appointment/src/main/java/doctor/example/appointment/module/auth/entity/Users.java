package doctor.example.appointment.module.auth.entity;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class Users {

    @Id
    private Long id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "user_name", nullable = false, length = 100)
    private String name;
    @Column(name = "user_email", nullable = false, length = 100)
    private String email;
    @Column(name = "user_password", nullable = false, length = 100)
    private String password;
    @Column(name = "role", nullable = false, length = 100)
    private  Role role;

    public Users(){}

    public Users(Long id, String name, String email, String password, Role role){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void setId(Long id){this.id = id;}
    public void setName(String name ){this.name = name;}
    public void setEmail(String email){this.email = email;}
    public  void setPassword(String password){this.password = password;}
    public  void setRole(Role role) {this.role = role;}


    public Long getId(){return id;}
    public String getName(){
        return name;
    }
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public Role getRole(){return role;}



}

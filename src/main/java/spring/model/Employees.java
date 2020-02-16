package spring.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Employees")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Employees implements HibernateEntity {

    // employee removing will result in car removing
    @OneToMany(mappedBy = "employees", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Cars> cars;

    @ManyToMany(mappedBy = "employeesSet", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Printers> printersSet;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude // in order to check if the same employee but with different id is being added
    @Column(name = "ID")
    private int id;

    @Column(name = "FirstName")
    @NonNull
    private String firstName;

    @Column(name = "LastName")
    @OrderColumn
    @NonNull
    private String lastName;

    @Column(name = "Address")
    @NonNull
    private String address;

    @Column(name = "City")
    @NonNull
    private String city;

    @Column(name = "Salary")
    @NonNull
    @EqualsAndHashCode.Exclude
    private int salary;

    @Column(name = "Age")
    @NonNull
    private int age;

    @Column(name = "StartJobDate")
    @NonNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @EqualsAndHashCode.Exclude
    private LocalDate startJobDate;

    @Column(name = "Benefit")
    @NonNull
    @EqualsAndHashCode.Exclude
    private int benefit;

    @Column(name = "Email")
    @Getter
    @Setter
    private String email;

    public Employees(String firstName, String lastName, String address, String city, int salary, int age, LocalDate startJobDate, int benefit, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.salary = salary;
        this.age = age;
        this.startJobDate = startJobDate;
        this.benefit = benefit;
        this.email = email;
    }

    public Employees(int id, String firstName, String lastName, String address, String city, int salary, int age, LocalDate startJobDate, int benefit, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.salary = salary;
        this.age = age;
        this.startJobDate = startJobDate;
        this.benefit = benefit;
        this.email = email;
    }

    public Set<Printers> getPrintersSet() {
        if (printersSet == null) {
            printersSet = new HashSet<>();
        }
        return printersSet;
    }

    public Employees() {
    }

    public Employees(int id, String firstName, String lastName, String address, String city, int age, int salary, LocalDate startJobDate, int benefit) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.age = age;
        this.salary = salary;
        this.startJobDate = startJobDate;
        this.benefit = benefit;
    }
}

package spring.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "Cars")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Cars implements HibernateEntity {

    @ManyToOne
//    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "EmployeeId") // można dać nullable = false i to będzie odpowiednik adnotacji @NonNull
    @ToString.Exclude // żeby nie wywalało stackoverflow exception
    @EqualsAndHashCode.Exclude // samochód nie decyduje o tym że to inna osoba w świetle equals i hashCode
    @NonNull
    private Employees employees;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "Brand")
    @OrderColumn
    @NonNull
    private String brand;

    @Column(name = "Model")
    @NonNull
    private String model;

    @Column(name = "RegistrationDate")
    @NonNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate registrationDate;


    public Cars() {
    }

}

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
    @JoinColumn(name = "EmployeeId") //  nullable = false as equivalent of @NonNull annotation
    @ToString.Exclude // in order not to throw stack overflow exception
    @EqualsAndHashCode.Exclude // car doesn't influence hashCode and equals
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

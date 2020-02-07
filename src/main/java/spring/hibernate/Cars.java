package spring.hibernate;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

//    Grupa 2 tworzy repozytorium publiczne na githubie i daje dostęp swojemu partnerowi/partnerce
//    Grupa 1 na zajęcia 25.01 Przepina nasz projekt na encje hibernate
//    Grupa 2 Dodaje mapowanie dla tabeli Cars z adnotacją @OneToMany


@Entity
@Table(name = "Cars")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Cars implements HibernateEntity {

    @ManyToOne(cascade = CascadeType.PERSIST)
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

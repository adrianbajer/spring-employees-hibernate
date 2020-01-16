package spring.hibernate;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

//    Grupa 2 tworzy repozytorium publiczne na githubie i daje dostęp swojemu partnerowi/partnerce
//    Grupa 1 na zajęcia 25.01 Przepina nasz projekt na encje hibernate
//    Grupa 2 Dodaje mapowanie dla tabeli Cars z adnotacją @OneToMany


@Entity
@Table(name = "Cars")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Cars {

    @ManyToOne
    @JoinColumn(name = "Employees")
    private Employees employees;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "Employee_ID")
    @NonNull
    private int employee_Id;

    @Column(name = "Name")
    @NonNull
    private String name;

    @Column(name = "Model")
    @NonNull
    private String model;

    @Column(name = "RegistrationDate")
    @NonNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date registrationDate;


    public Cars() {
    }
}

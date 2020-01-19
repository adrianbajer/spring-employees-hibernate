package spring.hibernate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

//    Grupa 2 tworzy repozytorium publiczne na githubie i daje dostęp swojemu partnerowi/partnerce
//    Grupa 1 na zajęcia 25.01 Przepina nasz projekt na encje hibernate
//    Grupa 2 Dodaje mapowanie dla tabeli Cars z adnotacją @OneToMany


@Entity
@Table(name = "Cars")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Cars {

    // usunięcie samochodu nie spowoduje usunięcia pracownika
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "EmployeeId")
    @NonNull
    private Employees employees;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "Brand")
    @NonNull
    private String brand;

    @Column(name = "Model")
    @NonNull
    private String model;

    @Column(name = "RegistrationDate")
    @NonNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date registrationDate;


    public Cars() {
    }

    // toString wygenerowane przez Lombok zapętlało się z toString z Employee i dawało StackOverFlow exception
    @Override
    public String toString() {
        return "Cars{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", registration date=" + registrationDate + '\'' +
                ", employee's id='" + employees.getId() +
                '}';
    }
}

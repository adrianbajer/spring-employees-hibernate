package spring.hibernate;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Printers")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Printers implements HibernateEntity {

    @ManyToMany
    @JoinColumn(name = "EmployeeId")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Employees> employeesList;

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

    @Column(name = "Color")
    @NonNull
    private boolean isColor;

    @Column(name = "Laser")
    @NonNull
    private boolean isLaser;

    public Printers() {
    }
}

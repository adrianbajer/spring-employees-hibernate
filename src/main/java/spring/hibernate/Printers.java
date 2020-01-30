package spring.hibernate;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Printers")
@Data
@RequiredArgsConstructor
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
    private Boolean isColor;

    @Column(name = "Laser")
    @NonNull
    private Boolean isLaser;

    public Printers() {
    }

    public Printers(List<Employees> employeesList, @NonNull String brand, @NonNull String model,
                    @NonNull Boolean isColor, @NonNull Boolean isLaser) {
        this.employeesList = employeesList;
        this.brand = brand;
        this.model = model;
        this.isColor = isColor;
        this.isLaser = isLaser;
    }

    public Printers(List<Employees> employeesList, int id, @NonNull String brand, @NonNull String model,
                    @NonNull Boolean isColor, @NonNull Boolean isLaser) {
        this.employeesList = employeesList;
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.isColor = isColor;
        this.isLaser = isLaser;
    }

    public Printers(int id, @NonNull String brand, @NonNull String model,
                    @NonNull boolean isColor, @NonNull boolean isLaser) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.isColor = isColor;
        this.isLaser = isLaser;
    }
}


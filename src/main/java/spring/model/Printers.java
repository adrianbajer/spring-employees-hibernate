package spring.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Printers")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Printers implements HibernateEntity {

    // join table annotation needs to be here, otherwise the employee_printer table won't fill with data
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "Employees_Printers",
            joinColumns = {@JoinColumn(name = "printerId", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "employeeId", referencedColumnName = "ID")})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Employees> employeesSet;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
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

    public Set<Employees> getEmployeesSet() {
        if (employeesSet == null) {
            employeesSet = new HashSet<>();
        }
        return employeesSet;
    }

    public Printers() {
    }

    public Printers(Set<Employees> employeesSet, @NonNull String brand, @NonNull String model,
                    @NonNull Boolean isColor, @NonNull Boolean isLaser) {
        this.employeesSet = employeesSet;
        this.brand = brand;
        this.model = model;
        this.isColor = isColor;
        this.isLaser = isLaser;
    }

    public Printers(int id, @NonNull String brand, @NonNull String model,
                     @NonNull Boolean isColor, @NonNull Boolean isLaser) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.isColor = isColor;
        this.isLaser = isLaser;
    }

}


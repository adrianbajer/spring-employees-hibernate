package spring.hibernate;

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

    @ManyToMany(mappedBy = "printersSet", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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

    // zgodnie z tym co znalazłam online trzeba dodać takie metody
    public void addEmployee(Employees employee) {
        employeesSet.add(employee);
        employee.getPrintersSet().add(this);
    }

    public void removeEmployee(Employees employee) {
        employeesSet.remove(employee);
        employee.getPrintersSet().remove(this);
    }

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
                    @NonNull boolean isColor, @NonNull boolean isLaser) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.isColor = isColor;
        this.isLaser = isLaser;
    }
}


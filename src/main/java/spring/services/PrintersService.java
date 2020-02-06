package spring.services;

import spring.hibernate.Printers;

import java.util.List;

public interface PrintersService {

    void create(Printers printers);

    List<Printers> getAll();

    void update(Printers printers);

    void delete(Printers printers);
}

package spring.services;

import org.springframework.stereotype.Service;
import spring.hibernate.Employees;
import spring.hibernate.Printers;
import spring.repository.PrintersRepository;

import java.util.List;

@Service
public class PrintersServiceImpl implements PrintersService {

    private PrintersRepository printersRepository;

    public PrintersServiceImpl(PrintersRepository printersRepository) {
        this.printersRepository = printersRepository;
    }

    @Override
    public void create(Printers printers) {
        printersRepository.save(printers);
    }

    @Override
    public List<Printers> getAll() {
        return printersRepository.findAll();
    }

    @Override
    public void update(Printers printers) {
        printersRepository.save(printers);
    }

    @Override
    public void delete(Printers printers) {
        printersRepository.delete(printers);
    }
}

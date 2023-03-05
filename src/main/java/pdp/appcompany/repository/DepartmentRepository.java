package pdp.appcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pdp.appcompany.entity.Company;
import pdp.appcompany.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    boolean existsByName(String name);

    boolean existsByNameAndCompanyId(String name, Integer id);


}

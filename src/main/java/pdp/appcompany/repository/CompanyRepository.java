package pdp.appcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pdp.appcompany.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    boolean existsByCorpName(String corpName);
    boolean existsByCorpNameAndIdNot(String corpName, Integer id);
}

package pdp.appcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pdp.appcompany.entity.Worker;


public interface WorkerRepository extends JpaRepository<Worker, Integer> {

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Integer id);
}

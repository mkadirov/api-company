package pdp.appcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pdp.appcompany.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}

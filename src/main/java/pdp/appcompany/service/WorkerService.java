package pdp.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdp.appcompany.entity.Address;
import pdp.appcompany.entity.Department;
import pdp.appcompany.entity.Worker;
import pdp.appcompany.payload.ApiResponse;
import pdp.appcompany.payload.WorkerDto;
import pdp.appcompany.repository.AddressRepository;
import pdp.appcompany.repository.DepartmentRepository;
import pdp.appcompany.repository.WorkerRepository;

import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    /**
     * POST method to add new Worker
     * @param workerDto model class for Worker
     */
    public ApiResponse addWorker(WorkerDto workerDto){
        if(workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber())){
            return new ApiResponse("PhoneNumber exists", false);
        }
        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (optionalAddress.isEmpty()){
            return new ApiResponse("Address not found", false);
        }
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (optionalDepartment.isEmpty()){
            return new ApiResponse("Department not found", false);
        }
        Worker worker = new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setAddress(optionalAddress.get());
        worker.setDepartment(optionalDepartment.get());
        workerRepository.save(worker);
        return new ApiResponse("Successfully added", true);
    }

    /**
     * GET method to get list of workers
     * @return message, boolean, List
     */
    public ApiResponse getWorkerList(){
        return new ApiResponse("Successfully retrieved", true, workerRepository.findAll());
    }

    /**
     * GET method to get Worker by ID
     * @param id worker id
     * @return message, boolean, Worker
     */
    public ApiResponse getWorkerByID(Integer id){
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.map(worker ->
                new ApiResponse("Successfully retrieved", true, worker)).orElseGet(() ->
                new ApiResponse("Worker not found", false));
    }

    /**
     * PUT method to edit Worker by ID
     * @param id worker id
     * @param workerDto model class for Worker
     * @return message about result and boolean
     */
    public ApiResponse editWorker(Integer id, WorkerDto workerDto){
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (optionalWorker.isEmpty()){
            return new ApiResponse("Worker not found", false);
        }
        if(workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id)){
            return new ApiResponse("PhoneNumber exists", false);
        }
        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (optionalAddress.isEmpty()){
            return new ApiResponse("Address not found", false);
        }
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (optionalDepartment.isEmpty()){
            return new ApiResponse("Department not found", false);
        }
        Worker worker = optionalWorker.get();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setAddress(optionalAddress.get());
        worker.setDepartment(optionalDepartment.get());
        workerRepository.save(worker);
        return new ApiResponse("Successfully added", true);
    }

    /**
     * DELETE method to delete  worker by id
     * @param id worker id
     * @return message about result and boolean
     */
    public ApiResponse deleteWorker(Integer id){
        if(workerRepository.existsById(id)){
            workerRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        }
        return new ApiResponse("Worker not found", false);
    }
}

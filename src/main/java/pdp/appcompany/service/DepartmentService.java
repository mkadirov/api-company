package pdp.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdp.appcompany.entity.Company;

import pdp.appcompany.entity.Department;
import pdp.appcompany.payload.ApiResponse;
import pdp.appcompany.payload.DepartmentDto;
import pdp.appcompany.repository.CompanyRepository;
import pdp.appcompany.repository.DepartmentRepository;

import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CompanyRepository companyRepository;


    /**
     * POST method to add new Department
     * @param departmentDto model class for Department class
     */
    public ApiResponse addDepartment(DepartmentDto departmentDto){
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (optionalCompany.isEmpty()){
            return new ApiResponse("Company not found", false);
        }
        if(departmentRepository.existsByNameAndCompanyId(departmentDto.getName(), departmentDto.getCompanyId())){
            return new ApiResponse("Department exists", false);
        }
        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new ApiResponse("Successfully added", true);
    }

    /**
     * GET method to get list of Departments
     * @return message about result, boolean, List
     */
    public ApiResponse getDepartmentList(){
        return new ApiResponse("Successfully retrieved", true, departmentRepository.findAll());
    }


    /**
     * GET method to get Department by ID
     * @param id department id
     * @return model class with message, boolean and object
     */
    public ApiResponse getDepartmentByID(Integer id){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.map(department ->
                new ApiResponse("Successfully retrieved", true, department)).orElseGet(() ->
                new ApiResponse("Department not found", false));
    }

    /**
     * PUT method to update Department by ID
     * @param id Department id
     * @param departmentDto model class for Department class
     * @return model class with message, boolean
     */
    public ApiResponse editDepartment(Integer id, DepartmentDto departmentDto){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isEmpty()){
            return new ApiResponse("Department not found", false);
        }
        Department department = optionalDepartment.get();
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (optionalCompany.isEmpty()){
            return new ApiResponse("Company not found", false);
        }
        if(!department.getName().equals(departmentDto.getName())){
            if (departmentRepository.existsByNameAndCompanyId(departmentDto.getName(), id)) {
                return new ApiResponse("Department exists", false);
            }
            department.setName(departmentDto.getName());
        }
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new ApiResponse("Successfully updated", true);

    }

    /**
     * DELETE method to delete department
     * @param id department id
     * @return model class with message, boolean
     */
    public ApiResponse deleteDepartment(Integer id){
        if(departmentRepository.existsById(id)){
            departmentRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        }
        return new ApiResponse("Department not found", false);
    }
}

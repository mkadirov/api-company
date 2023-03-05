package pdp.appcompany.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pdp.appcompany.payload.ApiResponse;
import pdp.appcompany.payload.CompanyDto;
import pdp.appcompany.service.CompanyService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    /**
     * POSt method to add new Company
     * @param companyDto model class for Company
     * @return message about result and boolean
     */
    @PostMapping
    public ResponseEntity<ApiResponse> addCompany(@Valid @RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.addCompany(companyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }

    /**
     * GET method to get  list of Companies
     * @return message about result and List, boolean
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getCompanyList(){
        return ResponseEntity.ok(companyService.getCompanyList());
    }

    /**
     * GET method to get company by ID
     * @param id company ID
     * @return message about result and boolean
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCompanyByID(@PathVariable Integer id){
        ApiResponse apiResponse = companyService.getCompanyById(id);
        return ResponseEntity.status(apiResponse.isSuccess()? 200:404).body(apiResponse);
    }

    /**
     * PUT method to upload Company byID
     * @param id company ID
     * @param companyDto model class for Company
     * @return result message and boolean
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editCompany(@PathVariable Integer id, @Valid @RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.editCompany(id, companyDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 202:409).body(apiResponse);
    }

    /**
     * DELETE method to delete Company by ID
     * @param id company ID
     * @return result message, boolean
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCompanyByID(@PathVariable Integer id){
        ApiResponse apiResponse = companyService.deleteCompanyByID(id);
        return ResponseEntity.status(apiResponse.isSuccess()? 200:404).body(apiResponse);
    }

    /**
     * Default message for bad request
     * @param exception
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handelValidationExceptions(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

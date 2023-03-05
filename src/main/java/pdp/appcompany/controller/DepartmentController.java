package pdp.appcompany.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pdp.appcompany.payload.ApiResponse;
import pdp.appcompany.payload.DepartmentDto;
import pdp.appcompany.service.DepartmentService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {


    @Autowired
    DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<ApiResponse> addDepartment(@Valid @RequestBody DepartmentDto departmentDto ){
        ApiResponse apiResponse = departmentService.addDepartment(departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 201:409).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getDepartmentList(){
        ApiResponse apiResponse = departmentService.getDepartmentList();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getDepartmentByID(@PathVariable Integer id){
        ApiResponse apiResponse = departmentService.getDepartmentByID(id);
        return ResponseEntity.status(apiResponse.isSuccess()? 200:404).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editDepartment(@PathVariable Integer id, @Valid @RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.editDepartment(id, departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 202: 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDepartment(@PathVariable Integer id){
        ApiResponse apiResponse = departmentService.deleteDepartment(id);
        return ResponseEntity.status(apiResponse.isSuccess()? 200:404).body(apiResponse);
    }
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

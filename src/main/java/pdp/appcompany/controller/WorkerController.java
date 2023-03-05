package pdp.appcompany.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pdp.appcompany.payload.ApiResponse;
import pdp.appcompany.payload.WorkerDto;
import pdp.appcompany.service.WorkerService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/worker")
public class WorkerController {

    @Autowired
    WorkerService workerService;


    @PostMapping
    public ResponseEntity<ApiResponse> addWorker(@Valid @RequestBody WorkerDto workerDto){
        ApiResponse apiResponse = workerService.addWorker(workerDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 201:409).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getWorkerList(){
        ApiResponse apiResponse = workerService.getWorkerList();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getWorkerByID(@PathVariable Integer id){
        ApiResponse apiResponse = workerService.getWorkerByID(id);
        return ResponseEntity.status(apiResponse.isSuccess()? 200:404).body(apiResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editWorker(@PathVariable Integer id, @Valid @RequestBody WorkerDto workerDto){
        ApiResponse apiResponse  = workerService.editWorker(id, workerDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteWorker(@PathVariable Integer id){
        ApiResponse apiResponse = workerService.deleteWorker(id);
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

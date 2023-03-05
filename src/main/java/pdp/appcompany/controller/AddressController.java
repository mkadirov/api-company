package pdp.appcompany.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pdp.appcompany.payload.AddressDto;
import pdp.appcompany.payload.ApiResponse;
import pdp.appcompany.service.AddressService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    /**
     * GET method to get List of Address
     * @return boolean and message, request status
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAddressList(){
        return ResponseEntity.ok(addressService.getAddressList());
    }

    /**
     * POST method to add new Address
     * @param addressDto model Class
     * @return boolean and message, request status
     */
    @PostMapping
    public ResponseEntity<ApiResponse> addAddress(@Valid @RequestBody AddressDto addressDto){
        ApiResponse apiResponse = addressService.addAddress(addressDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 201:409).body(apiResponse);
    }

    /**
     * GET method to get address by ID
     * @param id Address ID
     * @return  boolean and message, request status
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getAddressById(@PathVariable Integer id){
        ApiResponse apiResponse = addressService.getAddressById(id);
        return ResponseEntity.status(apiResponse.isSuccess()? 200:404).body(apiResponse);
    }

    /**
     * PUT method to upload address
     * @param id Address ID
     * @param addressDto model Class fo  Address
     * @return  boolean and message, request status
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editAddress(@PathVariable Integer id, @Valid @RequestBody AddressDto addressDto){
        ApiResponse apiResponse = addressService.editAddress(id, addressDto);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    /**
     * DELETE method to delete address by ID
     * @param id Address ID
     * @return boolean and message, request status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAddressById(@PathVariable Integer id){
        ApiResponse apiResponse = addressService.deleteAddress(id);
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

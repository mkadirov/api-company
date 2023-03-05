package pdp.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdp.appcompany.entity.Address;
import pdp.appcompany.payload.AddressDto;
import pdp.appcompany.payload.ApiResponse;
import pdp.appcompany.repository.AddressRepository;


import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    /**
     * GET method to get all addresses
     * @return apiResponse (String message, boolean, Object )
     */
    public ApiResponse getAddressList(){
        return new ApiResponse("Successfully retrieved",
                true, addressRepository.findAll());
    }

    /**
     * POST method to add new Address
     * @param addressDto model class to Address (street and homeNumber)
     * @return apiResponse class as result
     */
    public ApiResponse addAddress(AddressDto addressDto){
        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Successfully added", true);
    }

    /**
     * GET method to get Address by ID
     * @param id address ID
     */
    public ApiResponse getAddressById(Integer id){
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.map(address -> new ApiResponse(
                "Successfully retrieved", true, address)).orElseGet(() ->
                new ApiResponse("Address not found", false));
    }

    /**
     * PUT method to upload Address by ID
     * @param id address ID
     * @param addressDto model class
     */
    public ApiResponse editAddress(Integer id, AddressDto addressDto){
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isEmpty()){
            return new ApiResponse("Address not found", false);
        }
        Address address = optionalAddress.get();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Successfully uploaded", true);
    }

    /**
     * DELETE method to delete Address by Id
     * @param id address Id
     */
    public ApiResponse deleteAddress(Integer id){
        return addressRepository.existsById(id)?
                new ApiResponse("Successfully deleted", true):
                new ApiResponse("Address not found", false);
    }
}

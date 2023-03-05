package pdp.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import pdp.appcompany.entity.Address;
import pdp.appcompany.entity.Company;
import pdp.appcompany.payload.ApiResponse;
import pdp.appcompany.payload.CompanyDto;
import pdp.appcompany.repository.AddressRepository;
import pdp.appcompany.repository.CompanyRepository;

import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AddressRepository addressRepository;

    /**
     * POST method to add new Company
     * @param companyDto model class to Company
     * @return message about result and boolean
     */
    public ApiResponse addCompany(CompanyDto companyDto){
        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (optionalAddress.isEmpty()){
            return new ApiResponse("Address not found", false);
        }
        if(companyRepository.existsByCorpName(companyDto.getCorpName())){
            return new ApiResponse("Company exists", false);
        }
        Company company = new Company();
        company.setCorpName(companyDto.getCorpName());
        company.setAddress(optionalAddress.get());
        company.setDirectorName(companyDto.getDirectorName());
        companyRepository.save(company);
        return new ApiResponse("Successfully added", true);
    }

    /**
     * GET method to get List of companies
     * @return message about result and List
     */
    public ApiResponse getCompanyList(){
        return new ApiResponse("Successfully retrieved", true, companyRepository.findAll());
    }

    /**
     * GET method to get List of companies
     * @param id Company id
     * @return message about result and Company
     */
    public ApiResponse getCompanyById(Integer id){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.map(company ->
                new ApiResponse("Successfully retrieved", true, company)).orElseGet(()
                -> new ApiResponse("Company not found", false));
    }

    /**
     * PUT method to upload Company by ID
     * @param id company Id
     * @param companyDto model Class fo Company
     * @return message about result and boolean
     */
    public ApiResponse editCompany(Integer id, CompanyDto companyDto){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()){
            return new ApiResponse("Company not found", false);
        }
        Company company = optionalCompany.get();
        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (optionalAddress.isEmpty()){
            return new ApiResponse("Address not found", false);
        }
        if(companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(), id)){
            return new ApiResponse("Company exists", false);
        }
        company.setCorpName(companyDto.getCorpName());
        company.setAddress(optionalAddress.get());
        company.setDirectorName(companyDto.getDirectorName());
        companyRepository.save(company);
        return new ApiResponse("Successfully added", true);
    }

    /**
     * DELETE method to delete company by ID
     * @param id Company ID
     * @return message about result and boolean
     */
    public ApiResponse deleteCompanyByID(Integer id){
        if(companyRepository.existsById(id)){
            companyRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        }
        return new ApiResponse("Company not found", false);
    }

}

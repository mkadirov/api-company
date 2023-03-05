package pdp.appcompany.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    @NotNull(message = "Company name can't be empty")
    private String corpName;
    @NotNull(message = "Director can't be empty")
    private String directorName;
    @NotNull(message = "Address can't be empty")
    private int addressId;
}

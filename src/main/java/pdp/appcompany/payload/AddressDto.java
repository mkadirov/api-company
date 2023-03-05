package pdp.appcompany.payload;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    @NotNull(message = "Street must be present")
    private String street;
    @NotNull(message = "HomeNumber must be present")
    private String homeNumber;
}

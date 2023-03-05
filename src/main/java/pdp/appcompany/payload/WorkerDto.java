package pdp.appcompany.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDto {

    @NotNull(message = "Name can't be empty")
    private String name;
    @NotNull(message = "Phone can't be empty")
    private String phoneNumber;

    @NotNull(message = "Address can't be empty")
    private int addressId;

    @NotNull(message = "Department can't be empty")
    private int departmentId;

}

package pdp.appcompany.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {
    @NotNull(message = "name can't be empty")
    private String name;

    @NotNull(message = "Company can't be empty")
    private int companyId;
}

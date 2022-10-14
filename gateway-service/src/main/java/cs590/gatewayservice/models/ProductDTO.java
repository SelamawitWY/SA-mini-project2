package cs590.gatewayservice.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    private String name;
    private String vendor;
    private String category;
    private String availableUnit;
    private String price;
}

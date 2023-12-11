package by.xCaptin.restapi.dto;

import lombok.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductDTO {
    private long id;
    private String name;
    private int kcal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(kcal, that.kcal);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, kcal);
    }
}

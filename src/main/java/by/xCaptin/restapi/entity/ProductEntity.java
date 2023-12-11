package by.xCaptin.restapi.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity {
    private long id;
    private String name;
    private int kcal;

}

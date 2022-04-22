package com.gsdd.dw2.model.hateoas;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import org.springframework.hateoas.RepresentationModel;

@Generated
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class ElementModel extends RepresentationModel<ElementModel> {

    private Long elementId;

    @Schema(required = true, example = "Machine")
    @NotEmpty(message = "element name should not be empty")
    private String name;
}

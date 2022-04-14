package com.gsdd.dw2.model.hateoas;

import javax.validation.constraints.NotEmpty;
import org.springframework.hateoas.RepresentationModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;

@Generated
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class DigimonTypeModel extends RepresentationModel<DigimonTypeModel> {

  private Long digimonTypeId;

  @ApiModelProperty(required = true, example = "Data")
  @NotEmpty(message = "digimon type name should not be empty")
  private String name;
}
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
public class LevelModel extends RepresentationModel<LevelModel> {

  private Long levelId;

  @ApiModelProperty(required = true, example = "Rookie")
  @NotEmpty(message = "level name should not be empty")
  private String name;
}

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
public class LevelModel extends RepresentationModel<LevelModel> {

  private Long levelId;

  @Schema(required = true, example = "Rookie")
  @NotEmpty(message = "level name should not be empty")
  private String name;
}

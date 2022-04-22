package com.gsdd.dw2.model.hateoas;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import org.springframework.hateoas.RepresentationModel;

@Generated
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class DigimonXAttackModel extends RepresentationModel<DigimonXAttackModel> {

    @Schema(required = true, description = "Registered digimon id")
    @PositiveOrZero(message = "digimonId should be positive")
    @NotNull(message = "digimon should not be null")
    private Long digimonId;

    @Schema(required = true, description = "Registered attack id")
    @PositiveOrZero(message = "attackId should be positive")
    @NotNull(message = "attack should not be null")
    private Long attackId;
}

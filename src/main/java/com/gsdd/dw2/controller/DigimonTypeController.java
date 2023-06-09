package com.gsdd.dw2.controller;

import com.gsdd.dw2.model.hateoas.DigimonTypeModel;
import com.gsdd.dw2.persistence.entities.DigimonType;
import com.gsdd.dw2.service.AbstractService;
import com.gsdd.dw2.service.DigimonTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Digimon Type CRUD operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/digimonTypes")
public class DigimonTypeController extends AbstractController<DigimonType, DigimonTypeModel> {

  private final DigimonTypeService digimonTypeService;

  @Override
  public Long getId(DigimonTypeModel model) {
    return model.getDigimonTypeId();
  }

  @Override
  public AbstractService<DigimonType, DigimonTypeModel> getService() {
    return digimonTypeService;
  }
}

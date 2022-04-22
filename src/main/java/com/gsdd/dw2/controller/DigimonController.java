package com.gsdd.dw2.controller;

import com.gsdd.dw2.model.hateoas.DigimonModel;
import com.gsdd.dw2.persistence.entities.Digimon;
import com.gsdd.dw2.service.AbstractService;
import com.gsdd.dw2.service.DigimonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Digimon CRUD operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/digimons")
public class DigimonController extends AbstractController<Digimon, DigimonModel> {

    private final DigimonService digimonService;

    @Override
    public Long getId(DigimonModel model) {
        return model.getDigimonId();
    }

    @Override
    public AbstractService<Digimon, DigimonModel> getService() {
        return digimonService;
    }

    @Override
    public DigimonModel defineModelWithLinks(DigimonModel model) {
        DigimonModel linkedModel = super.defineModelWithLinks(model);
        Link levelLink =
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(LevelController.class)
                                        .getById(linkedModel.getLevelId()))
                        .withRel("level");
        Link typeLink =
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(DigimonTypeController.class)
                                        .getById(linkedModel.getDigimonTypeId()))
                        .withRel("type");
        Link elementLink =
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(ElementController.class)
                                        .getById(linkedModel.getElementId()))
                        .withRel("element");
        linkedModel.add(levelLink, typeLink, elementLink);
        return linkedModel;
    }
}

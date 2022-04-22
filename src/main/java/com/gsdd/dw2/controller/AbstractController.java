package com.gsdd.dw2.controller;

import com.gsdd.dw2.service.AbstractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
public abstract class AbstractController<T, D extends RepresentationModel<D>> {

    public abstract Long getId(D model);

    public abstract AbstractService<T, D> getService();

    public D defineModelWithLinks(D model) {
        Link selfLink = generateSelfLink(getId(model));
        if (selfLink != null) {
            model.add(selfLink);
        }
        return model;
    }

    public Link generateSelfLink(Long id) {
        try {
            Link selfLink =
                    WebMvcLinkBuilder.linkTo(
                                    WebMvcLinkBuilder.methodOn(this.getClass()).getById(id))
                            .withSelfRel();
            return selfLink;
        } catch (Exception e) {
            log.error("could not add self link", e);
        }
        return null;
    }

    @Operation(summary = "Allows to retrieve all")
    @GetMapping
    public ResponseEntity<CollectionModel<D>> getAll() {
        List<D> models =
                getService().getAll().stream()
                        .map(this::defineModelWithLinks)
                        .collect(Collectors.toList());
        Link link = WebMvcLinkBuilder.linkTo(this.getClass()).withSelfRel();
        CollectionModel<D> result = CollectionModel.of(models, link);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Retrieve a single record by id",
            responses = {
                @ApiResponse(responseCode = "200", description = "Matching data"),
                @ApiResponse(
                        responseCode = "404",
                        description = "Can not find any data by given id")
            })
    @GetMapping("{id:[0-9]+}")
    public ResponseEntity<D> getById(@PathVariable("id") Long id) {
        return Optional.ofNullable(getService().getById(id))
                .map(this::defineModelWithLinks)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Store given data",
            responses = {
                @ApiResponse(responseCode = "200", description = "Save success"),
                @ApiResponse(
                        responseCode = "400",
                        description = "If some missing data or wrong payload")
            })
    @PostMapping
    public ResponseEntity<D> save(@Valid @RequestBody D model) {
        return Optional.ofNullable(getService().save(model))
                .map(this::defineModelWithLinks)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(
            summary = "Fully updates matching data",
            responses = {
                @ApiResponse(responseCode = "200", description = "Update success"),
                @ApiResponse(
                        responseCode = "400",
                        description = "If some missing data or wrong payload"),
                @ApiResponse(
                        responseCode = "404",
                        description = "Can not find any data by given id")
            })
    @PutMapping("{id:[0-9]+}")
    public ResponseEntity<D> update(@PathVariable("id") Long id, @Valid @RequestBody D model) {
        return Optional.ofNullable(getService().update(id, model))
                .map(this::defineModelWithLinks)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Partial update matching data",
            responses = {
                @ApiResponse(responseCode = "200", description = "Update success"),
                @ApiResponse(
                        responseCode = "400",
                        description = "If some missing data or wrong payload"),
                @ApiResponse(
                        responseCode = "404",
                        description = "Can not find any data by given id")
            })
    @PatchMapping("{id:[0-9]+}")
    public ResponseEntity<D> patch(@PathVariable("id") Long id, @RequestBody D model) {
        return Optional.ofNullable(getService().patch(id, model))
                .map(this::defineModelWithLinks)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete matching data",
            responses = {
                @ApiResponse(responseCode = "204", description = "Delete success"),
                @ApiResponse(
                        responseCode = "404",
                        description = "Can not find any data by given id")
            })
    @DeleteMapping("{id:[0-9]+}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        return Optional.ofNullable(getService().delete(id))
                .map(result -> ResponseEntity.noContent().build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

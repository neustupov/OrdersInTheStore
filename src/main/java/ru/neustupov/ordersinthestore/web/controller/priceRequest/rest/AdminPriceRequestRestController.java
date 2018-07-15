package ru.neustupov.ordersinthestore.web.controller.priceRequest.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.neustupov.ordersinthestore.View;
import ru.neustupov.ordersinthestore.model.PriceRequest;
import ru.neustupov.ordersinthestore.web.controller.priceRequest.AbstractPriceRequestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(AdminPriceRequestRestController.REST_URL)
public class AdminPriceRequestRestController extends AbstractPriceRequestController {

    public static final String REST_URL = "/rest/admin/priceRequests";

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PriceRequest> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PriceRequest get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceRequest> createWithLocation(@Validated(View.Web.class)
                                                           @RequestBody PriceRequest priceRequest) {

        PriceRequest created = super.create(priceRequest);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Validated(View.Web.class) @RequestBody PriceRequest priceRequest, @PathVariable("id") int id) {
        super.update(priceRequest, id);
    }
}

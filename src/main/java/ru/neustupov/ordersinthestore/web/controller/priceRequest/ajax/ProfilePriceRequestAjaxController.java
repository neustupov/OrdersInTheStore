package ru.neustupov.ordersinthestore.web.controller.priceRequest.ajax;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.neustupov.ordersinthestore.model.PriceRequest;
import ru.neustupov.ordersinthestore.web.controller.priceRequest.AbstractPriceRequestController;

import java.util.List;

@RestController
@RequestMapping(value = "/ajax/profile/priceRequest")
public class ProfilePriceRequestAjaxController extends AbstractPriceRequestController {

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PriceRequest get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PriceRequest> getAll() {
        return super.getAll();
    }
}

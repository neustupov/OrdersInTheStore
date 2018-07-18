package ru.neustupov.ordersinthestore.web.controller.priceRequest.ajax;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.neustupov.ordersinthestore.model.PriceRequest;
import ru.neustupov.ordersinthestore.web.controller.priceRequest.AbstractPriceRequestController;

import java.util.List;

@RestController
@RequestMapping(value = "/ajax/admin/priceRequests")
public class AdminPriceRequestAjaxController extends AbstractPriceRequestController{

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PriceRequest get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PriceRequest> getAll() {
        return super.getAllWithAllInformation();
    }

    @Override
    @PostMapping(value = "/{id}")
    public void ready(@PathVariable("id") int id, @RequestParam("ready") boolean ready) {
        super.ready(id, ready);
    }
}

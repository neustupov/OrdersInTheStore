package ru.neustupov.ordersinthestore.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.neustupov.ordersinthestore.View;
import ru.neustupov.ordersinthestore.model.User;
import ru.neustupov.ordersinthestore.service.UserService;

import java.net.URI;

import static ru.neustupov.ordersinthestore.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(RootRestController.REST_URL)
public class RootRestController {

    public static final String REST_URL = "/rest";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@Validated(View.Web.class) @RequestBody User user) {

        log.info("create {}", user);
        checkNew(user);
        User created = service.create(user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}

package ru.neustupov.ordersinthestore.web.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.ordersinthestore.service.TypeService;

public abstract class AbstractTypeController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private TypeService typeService;
}

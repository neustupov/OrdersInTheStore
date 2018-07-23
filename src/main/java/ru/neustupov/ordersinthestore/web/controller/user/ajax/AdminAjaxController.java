package ru.neustupov.ordersinthestore.web.controller.user.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.neustupov.ordersinthestore.View;
import ru.neustupov.ordersinthestore.model.User;
import ru.neustupov.ordersinthestore.to.UserTo;
import ru.neustupov.ordersinthestore.util.UserUtil;
import ru.neustupov.ordersinthestore.util.exception.IllegalRequestDataException;
import ru.neustupov.ordersinthestore.web.controller.user.AbstractUserController;

import java.util.List;

@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController extends AbstractUserController{

    @Autowired
    private MessageSource messageSource;

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@Validated(View.Web.class) UserTo userTo) {
        try {
            if (userTo.isNew()) {
                super.create(UserUtil.createNewFromTo(userTo));
            } else {
                super.update(userTo, userTo.getId());
            }
        } catch (DataIntegrityViolationException e) {
            throw new IllegalRequestDataException(messageSource.getMessage(EXCEPTION_DUPLICATE_EMAIL,
                    null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    @PostMapping(value = "/{id}")
    public void enable(@PathVariable("id") int id, @RequestParam("enabled") boolean enabled) {
        super.enable(id, enabled);
    }
}

package ru.neustupov.oedersinthestore.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.neustupov.ordersinthestore.model.Role;
import ru.neustupov.ordersinthestore.model.User;
import ru.neustupov.ordersinthestore.service.UserService;
import ru.neustupov.ordersinthestore.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static ru.neustupov.oedersinthestore.UserTestData.*;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("users").clear();
    }

    @Test
    public void create() throws Exception {
        User newUser = new User(null, "New", "new@yandex.ru", "newPass", Date.from(Instant.now()), Collections.singleton(Role.ROLE_SELLER));
        User created = service.create(newUser);
        newUser.setId(created.getId());
        assertMatch(service.getAll(), ADMIN, newUser, SELLER, MANAGER);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateEmailCreate() throws Exception {
        service.create(new User(null, "Seller_New", "seller@yandex.ru", "password", Date.from(Instant.now()), Collections.singleton(Role.ROLE_SELLER)));
    }

    @Test
    public void delete() throws Exception {
        service.delete(MANAGER_ID);
        assertMatch(service.getAll(), ADMIN, SELLER);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        User user = service.get(ADMIN_ID);
        assertMatch(user, ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void update() throws Exception {
        User updated = new User(SELLER);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_SELLER));
        service.update(updated);
        assertMatch(service.get(SELLER_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<User> all = service.getAll();
        assertMatch(all, ADMIN, SELLER, MANAGER);
    }

    /*//TODO need repair password validation
    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> service.create(new User(100500, "   ", "new@yandex.ru","Password", Date.from(Instant.now()), EnumSet.of(Role.ROLE_USER))), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(100500, "TestUser", "yandex.ru","Password", Date.from(Instant.now()), EnumSet.of(Role.ROLE_USER))), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(100500, "TestUser", "new@yandex.ru","", Date.from(Instant.now()), EnumSet.of(Role.ROLE_USER))), ConstraintViolationException.class);
       // validateRootCause(() -> service.create(new User(100500, "TestUser", "new@yandex.ru","Pass", Date.from(Instant.now()), EnumSet.of(Role.ROLE_USER))), ConstraintViolationException.class);
       // validateRootCause(() -> service.create(new User(100500, "TestUser", "new@yandex.ru","1234567890qwertyuiopsdfghjklzxcvbnmnbvcxzasdfghjklpoiuytrewq12345qwertyuioplkjhgfdsazxcvbnmnbvcxzasdf", Date.from(Instant.now()), EnumSet.of(Role.ROLE_USER))), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(100500, "TestUser", "new@yandex.ru","Password", null, EnumSet.of(Role.ROLE_USER))), ConstraintViolationException.class);
    }*/

    @Test
    public void testEnable() {
        service.enable(MANAGER_ID, false);
        Assert.assertFalse(service.get(MANAGER_ID).isEnabled());
        service.enable(SELLER_ID, true);
        Assert.assertTrue(service.get(SELLER_ID).isEnabled());
    }

    @Test
    public void getByEmail() throws Exception {
        User user = service.getByEmail("admin@yandex.ru");
        assertMatch(user, ADMIN);
    }

}


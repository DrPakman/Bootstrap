package habsida.spring.boot_security.demo;

import habsida.spring.boot_security.demo.models.Role;
import habsida.spring.boot_security.demo.repositories.RoleRepository;
import habsida.spring.boot_security.demo.service.RoleService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class SpringBootSecurityDemoApplicationTests {

	@Test
	void contextLoads() {
	}
	@Mock
	private RoleRepository roleRepository;

	@InjectMocks
	private RoleService roleService;

	public void RoleServiceTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindRoleByNames() {
		// Подготовка данных
		List<String> roleNames = Arrays.asList("ROLE_ADMIN", "ROLE_USER");
		Role adminRole = new Role(1L, "ROLE_ADMIN");
		Role userRole = new Role(2L, "ROLE_USER");

		// Настройка моков
		when(roleRepository.findByNameIn(roleNames)).thenReturn(Arrays.asList(adminRole, userRole));

		// Вызов метода
		List<Role> roles = roleService.findRoleByNames(roleNames);

		// Проверка результатов
		assertEquals(2, roles.size());
		assertEquals("ROLE_ADMIN", roles.get(0).getName());
		assertEquals("ROLE_USER", roles.get(1).getName());
	}

}

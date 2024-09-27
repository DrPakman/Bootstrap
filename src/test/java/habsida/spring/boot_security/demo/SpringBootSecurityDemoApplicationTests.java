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

}

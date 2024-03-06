import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;

@SpringBootTest
class EmployeeServiceTest {
	
	@Autowired
	private EmployeeService employeeService;
	
	@MockBean
	private EmployeeRepository employeeRepository;
	
	Employee employee;

	@BeforeEach
	void setUp() throws Exception {
		employee = Employee.builder()
						.ename("Vikas")
						.address("Bapunagar")
						.eid(1L)
						.salary(80000)
						.build();
		Mockito.when(employeeRepository.getEmployeeByName("Vikas")).thenReturn(employee);
	}

	@Test
	void testGetAllEmployees() {
		when(employeeRepository.findAll()).thenReturn(Stream
				.of(new Employee(1L, "Vikas", 80000, "Bapunagar"), new Employee(2L, "Akash", 70000, "Bapunagar")).collect(Collectors.toList()));
		assertEquals(2, employeeService.getAllEmployees().size());
	}

	@Test
	@DisplayName("Get Data based on Valid Employee name")
	void testGet() {
		String name = "Vikas";
		//Mockito.when(employeeRepository.getEmployeeByName(name)).thenReturn(employee);
		Employee emp = employeeService.getByName(name);
		assertEquals(name, emp.getEname());
	}

	@Test
	void testSave() {
		employeeService.save(employee);
		verify(employeeRepository,times(1)).save(employee);
		assertNotNull(employee.getEid());
		assertNotEquals(2, employee.getEid());
	}
	
	@Test
	void testSaveException() {
		NullPointerException exception = Assertions.assertThrows(NullPointerException.class, ()->{
			Employee emp = new Employee();
			emp.setEname(null);
			//emp.getEname();
			throw new NullPointerException("Null Objects are not Allowed");
		});
		assertEquals("Null Objects are not Allowed", exception.getMessage());
	}

	@Test
	void testDelete() {
		employeeService.delete(employee.getEid());
		doNothing().when(employeeRepository).deleteById(employee.getEid());
		verify(employeeRepository, times(1)).deleteById(employee.getEid());
		assertNotNull(employee.getEid());
	}

	@Test
	void testUpdate() {
		when(employeeRepository.save(employee)).thenReturn(employee);
		//or
		doReturn(employee).when(employeeRepository).save(employee);
		employee.setEname("Vikas Singh");
		Employee updatedEmployee = employeeService.update(employee);
		assertThat(updatedEmployee.getEname()).isEqualTo("Vikas Singh");
	}

}

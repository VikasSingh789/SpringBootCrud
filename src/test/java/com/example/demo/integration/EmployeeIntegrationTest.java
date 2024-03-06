import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeIntegrationTest {
	
	@LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;
	
	@Autowired 
	private EmployeeRepository employeeRepository;

	@BeforeEach
	void setUp() throws Exception {
		baseUrl = baseUrl.concat(":"+port).concat("/employee");
	}
	
	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}
	
	@Test
	void testSave() {
		Employee emp = new Employee(3L, "Vikas", 80000, "Bapunagar");
		Employee response = restTemplate.postForObject(baseUrl, emp, Employee.class);
        assertEquals("Vikas", response.getEname());
        assertEquals(1, employeeRepository.findAll().size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
    @Sql(statements = "INSERT INTO EMPLOYEE (eid, ename, salary, address) VALUES (4,'Abhishek', 60000, 'Bapunagar')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM EMPLOYEE WHERE ename='Abhishek'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testGetAllEmployees() {
		List<Employee> employees = restTemplate.getForObject("http://localhost:"+port+"/employees", List.class);
		//if Running All Test cases, then 2 else 1
        assertEquals(2, employees.size());
        assertEquals(2, employeeRepository.findAll().size());
	}
	

	@Test
	@Sql(statements = "INSERT INTO EMPLOYEE (eid, ename, salary, address) VALUES (1,'Krishna', 60000, 'Bapunagar')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM EMPLOYEE WHERE eid=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testGet() { 
		Employee employee = restTemplate.getForObject(baseUrl + "/{id}", Employee.class, 1);
        assertAll(
                () -> assertNotNull(employee),
                () -> assertEquals(1, employee.getEid()),
                () -> assertEquals("Krishna", employee.getEname())
        );
	}

	@Test
	@Sql(statements = "INSERT INTO EMPLOYEE (eid, ename, salary, address) VALUES (8,'ABC', 60000, 'Bapunagar')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	void testDelete() {
		int recordCount=employeeRepository.findAll().size();
        assertEquals(1, recordCount);
        restTemplate.delete(baseUrl+"/delete/{id}", 8);
        assertEquals(0, employeeRepository.findAll().size());
	}

	@Test
	@Sql(statements = "INSERT INTO EMPLOYEE (eid, ename, salary, address) VALUES (2,'ABC', 60000, 'Bapunagar')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM EMPLOYEE WHERE eid=2", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void testUpdate() {
		Employee employee = new Employee(2L, "ABC", 60000, "Langar House");
        restTemplate.put(baseUrl+"/update/{id}", employee, 2);
        Employee employeeFromDB = employeeRepository.findById(2L).get();
        assertAll(
                () -> assertNotNull(employeeFromDB),
                () -> assertEquals("Langar House", employeeFromDB.getAddress())
        );
	}

}

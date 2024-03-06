import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.employee.model.Employee;
import com.employee.service.EmployeeService;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EmployeeService employeeService;
	
	private Employee employee;
	
	String employeeJson = "{\n"
			+ "    \"ename\":\"Vikas\",\n"
			+ "    \"address\":\"Bapunagar\",\n"
			+ "    \"salary\":80000\n"
			+ "}";

	@BeforeEach
	void setUp() throws Exception {
		employee = Employee.builder()
						.ename("Vikas")
						.address("Bapunagar")
						.eid(1L)
						.salary(80000)
						.build();
						
	}

	@Test
	void testGet() throws Exception {
		Mockito.when(employeeService.get(1L)).thenReturn(employee);
		mockMvc.perform(get("/employee/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.ename").
		                value(employee.getEname()));
	}

	@Test
	void testGetByEmployeeName() throws Exception {
		Mockito.when(employeeService.getByName("Vikas")).thenReturn(employee);
		mockMvc.perform(get("/employee/getEmployeeByName/Vikas")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.ename").
		                value(employee.getEname()));
	}

	@Test
	void testSave() throws Exception {
		Employee inputEmployee = Employee.builder()
				.ename("Vikas")
				.address("Bapunagar")
				.salary(80000)
				.build();
		Mockito.when(employeeService.save(inputEmployee)).thenReturn(employee);
		mockMvc.perform(MockMvcRequestBuilders.post("/employee")
				.contentType(MediaType.APPLICATION_JSON)
				.content(employeeJson))
		        .andExpect(status().isOk());
	}
	
	@Test
	void testGetAllEmployees() throws Exception {
		when(employeeService.getAllEmployees()).thenReturn(Collections.singletonList(employee));
		mockMvc.perform(get("/employees")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].ename").
		                value(employee.getEname()));
	}

	@Test
	void testDelete() throws Exception {
		//when(employeeService.delete(employee.getEid())).thenReturn(true);
		doNothing().when(employeeService).delete(employee.getEid());
        mockMvc.perform(delete("/employee/delete/" + employee.getEid()))
            .andExpect(status().isOk());
	}

}

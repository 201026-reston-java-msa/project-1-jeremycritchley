package com.revature;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.revature.dao.ReimDAO;
import com.revature.dao.UserDAO;
import com.revature.dto.ReimDTO;
import com.revature.dto.UserDTO;
import com.revature.models.ReimStatus;
import com.revature.models.ReimType;
import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.EmployeeServiceImpl;
import com.revature.services.LoginServiceImpl;
import com.revature.services.ManagerServiceImpl;

public class DriverTest {
	
	@Mock
	private UserDAO userd;
	
	@Mock
	private ReimDAO reimd;
	
	@InjectMocks
	EmployeeServiceImpl empServ;
	
	@InjectMocks
	ManagerServiceImpl manServ;
	
	@InjectMocks
	LoginServiceImpl ls;
	
	

	@Before
	public void setUp() throws Exception {
		// TODO: Make real unit tests using Mockito to mock DAOs for Service layer
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testUpdateInfo() {
		UserDTO udto = Mockito.mock(UserDTO.class);
		User u = new User(1, "username", "password", "firstName", "lastName", "email", new Role(1, "MANAGER"));
		when(udto.createUserInstance()).thenReturn(u);
		when(udto.getUserInstance()).thenReturn(u);
		
		when(userd.update(u)).thenReturn(true);
		
		assertEquals(empServ.updateInfo(udto), true);
	}
	
	@Test
	public void testUpdateInfoFailure() {
		UserDTO udto = Mockito.mock(UserDTO.class);
		User u = new User(-11, null, "password", "firstName", "lastName", "email", new Role(1, "MANAGER"));
		when(udto.createUserInstance()).thenReturn(u);
		when(udto.getUserInstance()).thenReturn(u);
		
		when(userd.update(u)).thenReturn(true);
		
		assertEquals(empServ.updateInfo(udto), false);
	}
	
	@Test
	public void testSubmitReim() {
		Reimbursement reim = new Reimbursement(500.00, "23/11/2020 14:36:10", null, "description", new User(), null, new ReimStatus(1), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance()).thenReturn(reim);
		when(rdto.getReimInstance()).thenReturn(reim);
		
		when(reimd.insert(reim)).thenReturn(1);
		
		assertEquals(empServ.submitReim(rdto), 1);
	}
	
	@Test
	public void testSubmitReimFailAmount() {
		Reimbursement reim = new Reimbursement(-500.00, "23/11/2020 14:36:10", null, "description", new User(), null, new ReimStatus(1), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance()).thenReturn(reim);
		when(rdto.getReimInstance()).thenReturn(reim);
		
		when(reimd.insert(reim)).thenReturn(1);
		
		assertEquals(empServ.submitReim(rdto), 0);
	}
	
	@Test
	public void testSubmitReimFailNullAuthor() {
		Reimbursement reim = new Reimbursement(500.00, "23/11/2020 14:36:10", null, "description", null, null, new ReimStatus(1), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance()).thenReturn(reim);
		when(rdto.getReimInstance()).thenReturn(reim);
		
		when(reimd.insert(reim)).thenReturn(1);
		
		assertEquals(empServ.submitReim(rdto), 0);
	}
	
	@Test
	public void testViewReimByStatus() {
		Reimbursement reim = new Reimbursement(500.00, "23/11/2020 14:36:10", null, "description", new User(), null, new ReimStatus(1), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance()).thenReturn(reim);
		when(rdto.getReimInstance()).thenReturn(reim);
		
		when(reimd.selectAll("Author_FK", "1")).thenReturn(new ArrayList<Reimbursement>());
		
		assertNotEquals(empServ.viewRiemsByStatus(1, false), null);
		
	}
	
	@Test
	public void testViewReimByStatusUserDoesNotExist() {
		Reimbursement reim = new Reimbursement(500.00, "23/11/2020 14:36:10", null, "description", new User(), null, new ReimStatus(1), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance()).thenReturn(reim);
		when(rdto.getReimInstance()).thenReturn(reim);
		
		when(reimd.selectAll("Author_FK", "1")).thenReturn(new ArrayList<Reimbursement>());
		
		assertEquals(empServ.viewRiemsByStatus(0, false), null);
		
	}
	
	@Test
	public void testViewReimByStatusInvalidStatus() {
		Reimbursement reim = new Reimbursement(500.00, "23/11/2020 14:36:10", null, "description", new User(), null, new ReimStatus(1), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance()).thenReturn(reim);
		when(rdto.getReimInstance()).thenReturn(reim);
		
		when(reimd.selectAll("Author_FK", "0")).thenReturn(new ArrayList<Reimbursement>());
		
		assertEquals(empServ.viewRiemsByStatus(1, false), null);
		
	}
	
	@Test
	public void testApproveReim() {
		Reimbursement reim = new Reimbursement(1, 500.00, "23/11/2020 14:36:10", "23/11/2020 14:36:10", "description", new User(), new User(), new ReimStatus(2), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance()).thenReturn(reim);
		when(rdto.getReimInstance()).thenReturn(reim);
		
		when(reimd.update(reim)).thenReturn(true);
		
		assertEquals(manServ.approveReim(rdto, 1), true);
	}
	
	@Test
	public void testApproveReimFail() {
		Reimbursement reim = new Reimbursement(1, 500.00, "23/11/2020 14:36:10", "23/11/2020 14:36:10", "description", new User(), new User(), new ReimStatus(2), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance()).thenReturn(reim);
		when(rdto.getReimInstance()).thenReturn(reim);
		
		when(reimd.update(reim)).thenReturn(false);
		
		assertEquals(manServ.approveReim(rdto, 0), false);
	}
	
	@Test
	public void testDenyReim() {
		Reimbursement reim = new Reimbursement(1, 500.00, "23/11/2020 14:36:10", "23/11/2020 14:36:10", "description", new User(), new User(), new ReimStatus(3), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance()).thenReturn(reim);
		when(rdto.getReimInstance()).thenReturn(reim);
		
		when(reimd.update(reim)).thenReturn(true);
		
		assertEquals(manServ.denyReim(rdto, 1), true);
	}
	
	@Test
	public void testDenyReimFail() {
		Reimbursement reim = new Reimbursement(1, 500.00, "23/11/2020 14:36:10", "23/11/2020 14:36:10", "description", new User(), new User(), new ReimStatus(3), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance()).thenReturn(reim);
		when(rdto.getReimInstance()).thenReturn(reim);
		
		when(reimd.update(reim)).thenReturn(false);
		
		assertEquals(manServ.denyReim(rdto, 0), false);
	}
	
	@Test
	public void testViewAllEmployees() {
		Reimbursement reim = new Reimbursement(1, 500.00, "23/11/2020 14:36:10", "23/11/2020 14:36:10", "description", new User(), new User(), new ReimStatus(2), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance()).thenReturn(reim);
		when(rdto.getReimInstance()).thenReturn(reim);
		
		when(userd.selectAll("Role_FK", "2")).thenReturn(new ArrayList<User>());
		assertNotEquals(manServ.viewAllEmployees(), null);
	}
	
	@Test
	public void testViewReimsByStatusPending() {
		Reimbursement reim = new Reimbursement(1, 500.00, "23/11/2020 14:36:10", "23/11/2020 14:36:10", "description", new User(), new User(), new ReimStatus(2), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance()).thenReturn(reim);
		when(rdto.getReimInstance()).thenReturn(reim);
		
		when(reimd.selectAll("Status_FK", "1")).thenReturn(new ArrayList<Reimbursement>());
		
		assertNotEquals(manServ.viewRiemsByStatus(0, false), null);
		
	}
	
	@Test
	public void testViewReimsByStatusResolved() {
		Reimbursement reim = new Reimbursement(1, 500.00, "23/11/2020 14:36:10", "23/11/2020 14:36:10", "description", new User(), new User(), new ReimStatus(2), new ReimType(1));
		ReimDTO rdto = Mockito.mock(ReimDTO.class);
		when(rdto.createReimInstance()).thenReturn(reim);
		when(rdto.getReimInstance()).thenReturn(reim);
		
		when(reimd.selectAll("Status_FK!", "1")).thenReturn(new ArrayList<Reimbursement>());
		
		assertNotEquals(manServ.viewRiemsByStatus(0, true), null);
		
	}
	
	@Test
	public void testViewByUser() {
		UserDTO udto = Mockito.mock(UserDTO.class);
		User u = new User(1, "username", "password", "firstName", "lastName", "email", new Role(1, "MANAGER"));
		when(udto.createUserInstance()).thenReturn(u);
		when(udto.getUserInstance()).thenReturn(u);
		
		when(userd.selectByParam("username", "username")).thenReturn(u);
		
		assertNotEquals(manServ.viewByUser("username"), null);
	}
	
	@Test
	public void testViewByUserFail() {
		UserDTO udto = Mockito.mock(UserDTO.class);
		User u = new User(1, "username", "password", "firstName", "lastName", "email", new Role(1, "MANAGER"));
		when(udto.createUserInstance()).thenReturn(u);
		when(udto.getUserInstance()).thenReturn(u);
		
		when(userd.selectByParam("username", null)).thenReturn(null);
		
		assertEquals(manServ.viewByUser("username"), null);
	}
	
	@Test
	public void testLogin() {
		UserDTO udto = Mockito.mock(UserDTO.class);
		User u = new User(1, "username", "password", "firstName", "lastName", "email", new Role(1, "MANAGER"));
		when(udto.createUserInstance()).thenReturn(u);
		when(udto.getUserInstance()).thenReturn(u);
		
		when(userd.selectByParam("username", "username")).thenReturn(u);
		
		assertNotEquals(ls.login("username", "password"), null);
		
		
	}
	
	@Test
	public void testLoginInvalidPassword() {
		UserDTO udto = Mockito.mock(UserDTO.class);
		User u = new User(1, "username", "password", "firstName", "lastName", "email", new Role(1, "MANAGER"));
		when(udto.createUserInstance()).thenReturn(u);
		when(udto.getUserInstance()).thenReturn(u);
		
		when(userd.selectByParam("username", "username")).thenReturn(u);
		
		assertEquals(ls.login("username", "p"), null);
	}
	
	@Test
	public void testLoginInvalidUsername() {
		UserDTO udto = Mockito.mock(UserDTO.class);
		User u = new User(1, "username", "password", "firstName", "lastName", "email", new Role(1, "MANAGER"));
		when(udto.createUserInstance()).thenReturn(u);
		when(udto.getUserInstance()).thenReturn(u);
		
		when(userd.selectByParam("username", "username")).thenReturn(null);
		
		assertNotEquals(ls.login("username", "password"), null);
		
		
	}

}

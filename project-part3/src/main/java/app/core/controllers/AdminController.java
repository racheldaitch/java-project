package app.core.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.core.auth.UserCredentials;
import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exception.CouponSystemException;
import app.core.services.AdminServiceImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin/")
public class AdminController {

	@Autowired
	private AdminServiceImpl adminService;

	@PostMapping(path = "company-new", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.CREATED)
	public Company addCompany(@RequestBody Company company, HttpServletRequest req) throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return adminService.addCompany(company, user.getId());

	}

	@PutMapping(path = "company-update", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.CREATED)
	public Company updateCompany(@RequestBody Company company, HttpServletRequest req) throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return adminService.updateCompany(company, user.getId());
	}

	@DeleteMapping(path = "company/{companyID}", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCompany(@PathVariable int companyID, HttpServletRequest req) throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		adminService.deleteCompany(companyID, user.getId());
	}

	@GetMapping(path = "all-companies", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.OK)
	public List<Company> getAllCompany(HttpServletRequest req) throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return adminService.getAllCompany(user.getId());
	}

	@GetMapping(path = "company/{companyID}", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.OK)
	public Company getOneCompany(@PathVariable int companyID, HttpServletRequest req) throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return adminService.getOneCompany(companyID, user.getId());
	}

	@PostMapping(path = "customer-new", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.CREATED)
	public Customer addCustomer(@RequestBody Customer customer, HttpServletRequest req) throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return adminService.addCustomer(customer, user.getId());
	}

	@PutMapping(path = "customer-update", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.CREATED)
	public Customer updateCustomer(@RequestBody Customer customer, HttpServletRequest req)
			throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return adminService.updateCustomer(customer, user.getId());
	}

	@DeleteMapping(path = "customer/{customerID}", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCustomer(@PathVariable int customerID, HttpServletRequest req) throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		adminService.deleteCustomer(customerID, user.getId());
	}

	@GetMapping(path = "all-customers", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.OK)
	public List<Customer> getAllCustomers(HttpServletRequest req) throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return adminService.getAllCustomers(user.getId());
	}

	@GetMapping(path = "customer/{customerId}", headers = { HttpHeaders.AUTHORIZATION })
	@ResponseStatus(HttpStatus.OK)
	public Customer getOneCustomer(@PathVariable int customerId, HttpServletRequest req) throws CouponSystemException {
		UserCredentials user = (UserCredentials) req.getAttribute("user");
		return adminService.getOneCustomer(customerId, user.getId());
	}

}

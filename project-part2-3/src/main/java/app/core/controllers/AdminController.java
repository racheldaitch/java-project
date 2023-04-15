package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exception.CouponSystemException;
import app.core.services.AdminServiceImpl;

@CrossOrigin
@RestController
@RequestMapping("/api/admin/")
public class AdminController {

	@Autowired
	private AdminServiceImpl adminService;

	@PostMapping(path = "company-new")
	public Company addCompany(@ModelAttribute Company company) throws CouponSystemException {
		return adminService.addCompany(company);
	}

	@PutMapping(path = "company-update")
	public Company updateCompany(@ModelAttribute Company company) throws CouponSystemException {
		return adminService.updateCompany(company);
	}

	@DeleteMapping(path = "company/{companyID}")
	public void deleteCompany(@PathVariable int companyID) throws CouponSystemException {
		adminService.deleteCompany(companyID);
	}

	@GetMapping(path = "all-companies")
	public List<Company> getAllCompany() throws CouponSystemException {
		return adminService.getAllCompany();
	}

	@GetMapping(path = "company/{companyID}")
	public Company getOneCompany(@PathVariable int companyID) throws CouponSystemException {
		return adminService.getOneCompany(companyID);
	}

	@PostMapping(path = "customer-new")
	public void addCustomer(@RequestBody Customer customer) throws CouponSystemException {
		adminService.addCustomer(customer);
	}

	@PutMapping(path = "customer-update")
	public void updateCustomer(@RequestBody Customer customer) throws CouponSystemException {
		adminService.updateCustomer(customer);
	}

	@DeleteMapping(path = "customer/{customerID}")
	public void deleteCustomer(@PathVariable int customerID) throws CouponSystemException {
		adminService.deleteCustomer(customerID);
	}

	@GetMapping(path = "all-customers")
	public List<Customer> getAllCustomers() throws CouponSystemException {
		return adminService.getAllCustomers();
	}

	@GetMapping(path = "customer/{customerId}")
	public Customer getOneCustomer(@PathVariable int customerId) throws CouponSystemException {
		return adminService.getOneCustomer(customerId);
	}

}

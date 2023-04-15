package app.core.data.dao;

import java.util.List;

import app.core.beans.Company;
import app.core.beans.Coupon;
import app.core.exception.CouponSystemException;

public interface CompaniesDAO {
	/**
	 * The method receives a company's email and password, and checks in the
	 * database whether the company exists in the companies table.
	 * 
	 * @param email    - of a company.
	 * @param password - of a company.
	 * @return boolean value does the company exist by email and password.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	boolean isCompanyExists(String email, String password) throws CouponSystemException;

	/**
	 * The method receives a company's name and email and checks in the database
	 * whether the company exists in the companies table.
	 * 
	 * @param name  - of a company.
	 * @param email - of a company.
	 * @return boolean value does the company exist by name and email.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	boolean isCompanyNameEmailExists(String name, String email) throws CouponSystemException;

	/**
	 * The method receives a company's code and checks in the database whether the
	 * company exists in the companies table.
	 * 
	 * @param companyID - the company code.
	 * @return boolean value Whether the company exists using the company code.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	boolean isCompanyIDExists(int companyID) throws CouponSystemException;

	/**
	 * The method receives a coupon from the company and checks in the database
	 * whether the company already has a coupon with the same title as this coupon.
	 * 
	 * @param coupon of a company. Coupon with: company code, category type, title,
	 *               description, start date, end date, amount, price and image.
	 * @return boolean value if the company has a coupon with the same title as the
	 *         received coupon.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	boolean isCompanyTitleExists(Coupon coupon) throws CouponSystemException;

	/**
	 * The method receives a company and adds it to the companies table in the
	 * database. and initializes the company code according to the code the company
	 * received in the database.
	 * 
	 * @param company - company details - name, email and password.
	 * @throws CouponSystemException if adding the company to the database companies
	 *                               table failed.
	 */
	void addCompany(Company company) throws CouponSystemException;

	/**
	 * The method receives a company with updated details, and updates the company
	 * details in the database by the company code.
	 * 
	 * @param company - company details: name, email and password after updating.
	 * @throws CouponSystemException if the company update in the database fails.
	 */
	void updateCompany(Company company) throws CouponSystemException;

	/**
	 * The method receives a company code and deletes the company in the database in
	 * the companies table.
	 * 
	 * @param companyID - the company code.
	 * @throws CouponSystemException if the company delete in the database fails.
	 */
	void deleteCompany(int companyID) throws CouponSystemException;

	/**
	 * The method receives from Data Base the details of the registered companies
	 * from companies's table.
	 * 
	 * @return the list of the companies that exist in the database or an empty list
	 *         if there are no registered companies.
	 * @throws CouponSystemException if the operation of getting the companies list
	 *                               failed.
	 */
	List<Company> getAllCompany() throws CouponSystemException;

	/**
	 * The method receives a company code and extracts the company details from the
	 * companies table in the database.
	 * 
	 * @param companyID - the company code.
	 * @return company details in the database - id, name, email and password if a
	 *         company does not exist, an empty company will be returned.
	 * @throws CouponSystemException if the operation of getting the company from
	 *                               database failed.
	 */
	Company getOneCompany(int companyID) throws CouponSystemException;

	/**
	 * The method receives a company's email and password and returns all company
	 * details from the database.
	 * 
	 * @param email    - of a company.
	 * @param password - of a company.
	 * @return full company details from database: code, name, email and password.
	 *         or an empty company if it does not exist in the database.
	 * @throws CouponSystemException if failed to get company data from database.
	 */
	Company getCompany(String email, String password) throws CouponSystemException;

}

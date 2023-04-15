package app.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.core.entities.Company;
import app.core.exception.CouponSystemException;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	/**
	 * The method receives a company's email and password, and checks in the
	 * database whether the company exists in the companies table.
	 * 
	 * @param email    - of a company.
	 * @param password - of a company.
	 * @return boolean value does the company exist by email and password.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	boolean existsByEmailAndPassword(String email, String password) throws CouponSystemException;

	/**
	 * The method receives a company's name and email and checks in the database
	 * whether the company exists in the companies table.
	 * 
	 * @param name  - of a company.
	 * @param email - of a company.
	 * @return boolean value does the company exist by name and email.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	boolean existsByNameOrEmail(String name, String email) throws CouponSystemException;

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
	@Query(value = "SELECT CASE WHEN (select exists(select * from coupons where title = ? and company_id = ? )) = 1 THEN 'True' else 'False' END;", nativeQuery = true)
	boolean isCompanyTitleExists(String title, int companyId) throws CouponSystemException;

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
	Company findByEmailAndPassword(String email, String password) throws CouponSystemException;

}

package app.core.services;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exception.CouponSystemException;
import lombok.Getter;

@Service
@Transactional
@Scope("prototype")
public class CompanyServiceImpl extends ClientService implements CompanyService {

	@Getter
	private int companyId;

	/**
	 * The method receives a company's email and password, and checks against the
	 * database whether the login details are correct. If the login information is
	 * correct, the method initializes the companyID.
	 * 
	 * @param email    - of a company.
	 * @param password - of a company.
	 * @return boolean value if the login details are correct.
	 */
	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		if (!companyRepository.existsByEmailAndPassword(email, password)) {
			return false;
		}
		this.companyId = companyRepository.findByEmailAndPassword(email, password).getId();
		return true;
	}

	/**
	 * The method adds a coupon to the company that logged in after checking that
	 * the company does not have a coupon with the same title as the coupon it is
	 * adding.
	 * 
	 * @param coupon of a company - Coupon with: company code, category type, title,
	 *               description, start date, end date, amount, price and image.
	 * @throws CouponSystemException if the company has a coupon with the same title
	 *                               as the coupon you are adding.
	 */
	@Override
	public void addCoupon(Coupon coupon) throws CouponSystemException {
		if (companyRepository.isCompanyTitleExists(coupon.getTitle(), companyId)) {
			throw new CouponSystemException("There is a coupon with this title - can't add");
		}
		couponRepository.save(coupon);
	}

	/**
	 * The method receives a coupon after updating the details you wish to update,
	 * and checks that it exists in the database using The code of the coupon, if it
	 * exists, it updates the coupon details. The coupon code and company code
	 * cannot be updated.
	 * 
	 * @param coupon - Coupon with: company code, category type, title, description,
	 *               start date, end date, amount, price and image after updating.
	 * @throws CouponSystemException if the coupon that the company adds does not
	 *                               exist in the database.
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Coupon couponFromDb = couponRepository.findById(coupon.getId())
				.orElseThrow(() -> new CouponSystemException("coupon code cannot be updated"));
		if (coupon.getCompany().getId() != couponFromDb.getCompany().getId()) {
			throw new CouponSystemException("company id cannot be updated");
		}
		couponFromDb.setCategory(coupon.getCategory());
		couponFromDb.setTitle(coupon.getTitle());
		couponFromDb.setDescription(coupon.getDescription());
		couponFromDb.setStartDate(coupon.getStartDate());
		couponFromDb.setEndDate(coupon.getEndDate());
		couponFromDb.setAmount(coupon.getAmount());
		couponFromDb.setPrice(coupon.getPrice());
		couponFromDb.setImage(coupon.getImage());
		couponRepository.save(couponFromDb);
	}

	/**
	 * The method receives an id of a coupon and deletes the coupon from the
	 * database and also deletes the history of the purchase of this coupon from the
	 * purchase table, deletes the purchases of the coupon.
	 * 
	 * @param couponID - the coupon code.
	 * @throws CouponSystemException if coupon does not exist in the database.
	 */
	@Override
	public void deleteCoupon(int couponID) throws CouponSystemException {
		if (!couponRepository.existsById(couponID)) {
			throw new CouponSystemException("Coupon " + couponID + " does not exist - can't delete");
		}
		couponRepository.deleteById(couponID);
	}

	/**
	 * The method creates a list of all the coupons of the company that has logged
	 * in.
	 * 
	 * @return the list of the coupons of the company that has logged in.
	 * @throws CouponSystemException if the company has no coupons in the database.
	 */
	@Override
	public List<Coupon> getCompanyCoupons() throws CouponSystemException {
		List<Coupon> coupons = couponRepository.findByCompanyId(companyId);

		return coupons;
	}

	/**
	 * The method creates a list of all the coupons from the selected category, of
	 * the company that made a login.
	 * 
	 * @param category - the category of the coupon.
	 * @return the list of coupons according to the selected category of the entered
	 *         company.
	 * @throws CouponSystemException if the company has no coupons from this
	 *                               category.
	 */
	@Override
	public List<Coupon> getCompanyCoupons(Category category) throws CouponSystemException {
		List<Coupon> coupons = couponRepository.findByCompanyIdAndCategory(companyId, category);

		return coupons;
	}

	/**
	 * The method creates a list of all the coupons up to a selected maximum price,
	 * of the company that made a login.
	 * 
	 * @param maxPrice - maximum price of coupons you wish to receive.
	 * @return the list of coupons of the company that connected up to a selected
	 *         price.
	 * @throws CouponSystemException if the company has no coupons up to the
	 *                               selected price.
	 */
	@Override
	public List<Coupon> getCompanyCoupons(double maxPrice) throws CouponSystemException {
		List<Coupon> coupons = couponRepository.findByCompanyIdAndPriceLessThanEqual(companyId, maxPrice);

		return coupons;
	}

	/**
	 * The method extracts from the database the details of the company that made a
	 * login.
	 * 
	 * @return the details of the company that logged in.
	 * @throws CouponSystemException if the operation failed.
	 */
	@Override
	public Company getCompanyDetails() throws CouponSystemException {
		return companyRepository.findById(companyId)
				.orElseThrow(() -> new CouponSystemException("company " + companyId + " not found"));
	}

}

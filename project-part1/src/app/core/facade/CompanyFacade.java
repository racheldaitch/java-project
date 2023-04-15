package app.core.facade;

import java.util.List;

import app.core.beans.Category;
import app.core.beans.Company;
import app.core.beans.Coupon;
import app.core.exception.CouponSystemException;

public class CompanyFacade extends ClientFacade {

	private int companyId;

	public CompanyFacade() {
		super();

	}

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
		if (!companiesDAO.isCompanyExists(email, password)) {
			throw new CouponSystemException("Incorrect login details");
		}
		this.companyId = companiesDAO.getCompany(email, password).getId();
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
	public void addCoupon(Coupon coupon) throws CouponSystemException {
		if (companiesDAO.isCompanyTitleExists(coupon)) {
			throw new CouponSystemException("There is a coupon with this title - can't add");
		}
		couponsDAO.addCoupon(coupon);
		return;
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
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		if (couponsDAO.isCouponExists(coupon.getId())) {
			Coupon coupon1 = couponsDAO.getOneCoupon(coupon.getId());
			coupon1.setCategory(coupon.getCategory());
			coupon1.setTitle(coupon.getTitle());
			coupon1.setDescription(coupon.getDescription());
			coupon1.setStart_date(coupon.getStart_date());
			coupon1.setEnd_date(coupon.getEnd_date());
			coupon1.setAmount(coupon.getAmount());
			coupon1.setPrice(coupon.getPrice());
			coupon1.setImage(coupon.getImage());
			couponsDAO.updateCoupon(coupon1);
			return;
		}
		throw new CouponSystemException("Coupon " + coupon.getId() + " does not exist - can't update");
	}

	/**
	 * The method receives an id of a coupon and deletes the coupon from the
	 * database and also deletes the history of the purchase of this coupon from the
	 * purchase table, deletes the purchases of the coupon.
	 * 
	 * @param couponID - the coupon code.
	 * @throws CouponSystemException if coupon does not exist in the database.
	 */
	public void deleteCoupon(int couponID) throws CouponSystemException {
		if (couponsDAO.isCouponExists(couponID)) {
			couponsDAO.deleteCoupon(couponID);
			return;
		}
		throw new CouponSystemException("Coupon " + couponID + " does not exist - can't delete");
	}

	/**
	 * The method creates a list of all the coupons of the company that has logged
	 * in.
	 * 
	 * @return the list of the coupons of the company that has logged in.
	 * @throws CouponSystemException if the company has no coupons in the database.
	 */
	public List<Coupon> getCompanyCoupons() throws CouponSystemException {
		List<Coupon> coupons = couponsDAO.getAllCouponsOfCompany(companyId);
		if (coupons.isEmpty()) {
			throw new CouponSystemException("company " + companyId + " has no coupons");
		}
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
	public List<Coupon> getCompanyCoupons(Category category) throws CouponSystemException {
		List<Coupon> coupons = couponsDAO.getCategoryCouponsOfCompany(companyId, category);
		if (coupons.isEmpty()) {
			throw new CouponSystemException("company " + companyId + " has no coupons from category " + category);
		}
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
	public List<Coupon> getCompanyCoupons(double maxPrice) throws CouponSystemException {
		List<Coupon> coupons = couponsDAO.getCouponsMaxPriceOfCompany(companyId, maxPrice);
		if (coupons.isEmpty()) {
			throw new CouponSystemException("company " + companyId + " has no coupons up to price " + maxPrice);
		}
		return coupons;
	}

	/**
	 * The method extracts from the database the details of the company that made a
	 * login.
	 * 
	 * @return the details of the company that logged in.
	 * @throws CouponSystemException if the operation failed.
	 */
	public Company getCompanyDetails() throws CouponSystemException {
		return companiesDAO.getOneCompany(companyId);
	}

	/**
	 * @return the company code.
	 */
	public int getCompanyId() {
		return companyId;
	}

}

package app.core.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.core.beans.Category;
import app.core.beans.Coupon;
import app.core.data.ConnectionPool;
import app.core.exception.CouponSystemException;

public class CouponsDBDAO implements CouponsDAO {

	/**
	 * The method receives a coupon's code and checks in the database whether the
	 * coupon exists in the coupons table.
	 * 
	 * @param couponID - the coupon code.
	 * @return boolean value Whether the coupon exists exists in the database.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	@Override
	public boolean isCouponExists(int couponID) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select exists(select * from `coupons` where (id=?));";
		try (PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setInt(1, couponID);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int answer = rs.getInt(1);
			if (answer == 1) {
				return true;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Checking whether the coupon exists by couponID failed");

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return false;
	}

	/**
	 * The method receives a coupon and adds it to the coupons table in the
	 * database. and initializes the coupon code according to the code the coupon
	 * received in the database.
	 * 
	 * @param coupon - Coupon with: company code, category type, title, description,
	 *               start date, end date, amount, price and image.
	 * @throws CouponSystemException if adding the coupon to the database failed.
	 */
	@Override
	public void addCoupon(Coupon coupon) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "insert into `coupons` values(0, ? , ? , ? , ?, ?, ? , ?, ?, ?);";
		try (PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstm.setInt(1, coupon.getCompanyID());
			pstm.setString(2, coupon.getCategory().name());
			pstm.setString(3, coupon.getTitle());
			pstm.setString(4, coupon.getDescription());
			pstm.setDate(5, coupon.getStart_date());
			pstm.setDate(6, coupon.getEnd_date());
			pstm.setInt(7, coupon.getAmount());
			pstm.setDouble(8, coupon.getPrice());
			pstm.setString(9, coupon.getImage());
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			coupon.setId(id);
			System.out.println("Coupon " + coupon.getId() + " has been successfully added");
		} catch (SQLException e) {
			throw new CouponSystemException("Coupon add failed");

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	/**
	 * This method makes it possible to update the database on an existing coupon.
	 * The method receives an updated coupon and updates the details in the database
	 * according to the code number of the coupon.
	 * 
	 * @param coupon - Coupon with: company code, category type, title, description,
	 *               start date, end date, amount, price and image after updating.
	 * @throws CouponSystemException if the coupon update in the database fails.
	 */
	@Override
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "update `coupons` set company_id = ?, category = ?, title = ? , description = ?, start_date = ?, end_date = ?, amount = ? , price = ?, image = ? where id =?;";
		try (PreparedStatement pstm = con.prepareStatement(sql);) {
			pstm.setInt(1, coupon.getCompanyID());
			pstm.setString(2, coupon.getCategory().name());
			pstm.setString(3, coupon.getTitle());
			pstm.setString(4, coupon.getDescription());
			pstm.setDate(5, coupon.getStart_date());
			pstm.setDate(6, coupon.getEnd_date());
			pstm.setInt(7, coupon.getAmount());
			pstm.setDouble(8, coupon.getPrice());
			pstm.setString(9, coupon.getImage());
			pstm.setInt(10, coupon.getId());
			pstm.executeUpdate();
			System.out.println("Coupon " + coupon.getId() + " update successful");
		} catch (SQLException e) {
			throw new CouponSystemException("Update coupon faild");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	/**
	 * The method receives a coupon code and updates the amount of coupons of the
	 * same type in the database - subtracting 1 from the amount.
	 * 
	 * @param couponID - the coupon code.
	 * @throws CouponSystemException if updating the amount of coupons in the
	 *                               database failed.
	 */
	@Override
	public void updateAmountCoupon(int couponID) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "update `coupons` set amount = amount-1  where id = ?;";

		try (PreparedStatement pstm = con.prepareStatement(sql);) {
			pstm.setInt(1, couponID);
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to update coupon amount");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	/**
	 * The method receives a coupon code and deletes the coupon from the coupons
	 * table in the database and also deletes its purchase history, deletes the
	 * coupon from the purchases table.
	 * 
	 * @param couponID - the coupon code.
	 * @throws CouponSystemException if the coupon delete in the database failed.
	 */
	@Override
	public void deleteCoupon(int couponID) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "delete from `coupons` where id = ?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, couponID);
			pstmt.executeUpdate();
			System.out.println("Coupon " + couponID + " delete successfully");
		} catch (SQLException e) {
			throw new CouponSystemException("Delete coupon failed");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	/**
	 * The method receives a company code and deletes in the database from the
	 * coupons table all the company's coupons, and also deletes their purchase
	 * history, deletes the company's coupons from the purchases table.
	 * 
	 * @param companyID - the company code.
	 * @throws CouponSystemException if the deletion of the company's coupons from
	 *                               the coupons table and the purchases table in
	 *                               the database failed.
	 */
	@Override
	public void deleteCouponsOfCompany(int companyID) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "delete from `coupons` where company_id=?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, companyID);
			pstmt.executeUpdate();
			System.out.println("Coupons of company " + companyID + "  deleted successfully");
		} catch (SQLException e) {
			throw new CouponSystemException("Delete coupons of company " + companyID + " - failed");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	/**
	 * The method receives from the database the list of coupons in the coupons
	 * table.
	 * 
	 * @return list of the coupons that exist in the database or an empty list if
	 *         there are no registered coupons.
	 * @throws CouponSystemException if the operation of getting the coupons list
	 *                               from the database failed.
	 */
	@Override
	public List<Coupon> getAllCoupons() throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from `coupons`;";
		List<Coupon> couponsList = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyID(rs.getInt("company_id"));
				coupon.setCategory(Category.valueOf(rs.getString("category")));
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStart_date(rs.getDate("start_date"));
				coupon.setEnd_date(rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				couponsList.add(coupon);
			}

		} catch (SQLException e) {
			throw new CouponSystemException("Get all coupons failed");

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return couponsList;
	}

	/**
	 * The method receives by company code the list of all the company's coupons
	 * from the coupons table.
	 * 
	 * @param companyID - the company code.
	 * @return list of company coupons that exist in the database or an empty list
	 *         if there are no registered coupons to the company.
	 * @throws CouponSystemException if the operation of getting the company coupons
	 *                               list from the database failed.
	 */
	@Override
	public List<Coupon> getAllCouponsOfCompany(int companyID) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from `coupons` where company_id= ?;";
		List<Coupon> couponlist = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, companyID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyID(rs.getInt("company_id"));
				coupon.setCategory(Category.valueOf(rs.getString("category")));
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStart_date(rs.getDate("start_date"));
				coupon.setEnd_date(rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				couponlist.add(coupon);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Get all coupons from company " + companyID + " - failed");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return couponlist;
	}

	/**
	 * The method receives by company code the list of all the company's coupons
	 * from the coupons table according to the selected category.
	 * 
	 * @param companyID - the company code.
	 * @param category  - the category of the coupon.
	 * @return list of company coupons from the selected category that exist in the
	 *         database or an empty list if there are no registered coupons to the
	 *         company according to this choice.
	 * @throws CouponSystemException if the operation of receiving the company's
	 *                               coupons from the database according to the
	 *                               selected category failed.
	 */
	@Override
	public List<Coupon> getCategoryCouponsOfCompany(int companyID, Category category) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from coupons where company_id = ? and category = ?;";
		List<Coupon> couponsCategory = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, companyID);
			pstmt.setString(2, category.name());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyID(rs.getInt("company_id"));
				coupon.setCategory(Category.valueOf(rs.getString("category")));
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStart_date(rs.getDate("start_date"));
				coupon.setEnd_date(rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				couponsCategory.add(coupon);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Get all coupons of company from category - failed");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return couponsCategory;
	}

	/**
	 * The method receives by company code the list of all the company's coupons
	 * from the coupons table according up to a selected price.
	 * 
	 * @param companyID - the company code.
	 * @param maxPrice  - maximum price of coupons you wish to receive.
	 * @return list of company coupons up to a selected price that exist in the
	 *         database or an empty list if there are no registered coupons to the
	 *         company according to this choice.
	 * @throws CouponSystemException if the operation of receiving the company's
	 *                               coupons from the database up to a selected
	 *                               price failed..
	 */
	@Override
	public List<Coupon> getCouponsMaxPriceOfCompany(int companyID, double maxPrice) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from `coupons` where company_id= ? and price <=?;";
		List<Coupon> couponlist = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, companyID);
			pstmt.setDouble(2, maxPrice);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyID(rs.getInt("company_id"));
				coupon.setCategory(Category.valueOf(rs.getString("category")));
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStart_date(rs.getDate("start_date"));
				coupon.setEnd_date(rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				couponlist.add(coupon);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Get all coupons of company up to price - failed");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return couponlist;
	}

	/**
	 * The method receives a coupon code and receives the requested coupon details
	 * from the coupons table database.
	 * 
	 * @param couponID - the coupon code.
	 * @return coupon details in the database - id, company code, category type,
	 *         title, description, start date, end date, amount, price and image. if
	 *         a coupon does not exist, an empty coupon will be returned.
	 * @throws CouponSystemException if failed to get coupon from database.
	 */
	@Override
	public Coupon getOneCoupon(int couponID) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from `coupons` where id = ?;";
		Coupon coupon = new Coupon();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, couponID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyID(rs.getInt("company_id"));
				coupon.setCategory(Category.valueOf(rs.getString("category")));
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStart_date(rs.getDate("start_date"));
				coupon.setEnd_date(rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
			} else {
				throw new CouponSystemException("Coupon " + couponID + " does not exists");
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Get one coupon failed");

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return coupon;
	}

	/**
	 * The method adds coupon purchases to the database to the purchases table by
	 * customer ID number and coupon code. If the customer has a coupon with the
	 * same code as the coupon he wishes to purchase, the method will not allow him
	 * to purchase the coupon.
	 * 
	 * @param customerID - customer ID number.
	 * @param couponID   - the coupon code.
	 * @throws CouponSystemException if the customer already has a coupon of the
	 *                               same type in the database.
	 */
	@Override
	public void addCouponPurchase(int customerID, int couponID) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "insert into `customers_vs_coupons` values(?, ?);";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, customerID);
			pstmt.setInt(2, couponID);
			pstmt.executeUpdate();
			System.out.println("Adding a purchased coupon was successful");
		} catch (SQLException e) {
			throw new CouponSystemException("The purchase failed - The customer has an identical coupon");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	/**
	 * The method deletes the coupon purchase from the purchases table in the
	 * database.
	 * 
	 * @param customerID - customer ID number.
	 * @param couponID   - the coupon code.
	 * @throws CouponSystemException if deleting the purchase history from Database
	 *                               failed.
	 */
	@Override
	public void deleteCouponPurchase(int customerID, int couponID) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "delete from `customers_vs_coupons` where customer_id = ? and coupon_id=?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, customerID);
			pstmt.setInt(2, couponID);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new CouponSystemException("deleteCouponPurchase failed");

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	/**
	 * The method checks in the database in the coupons table by coupon code if the
	 * coupon is in stock and also if the expiration date of the coupon has not
	 * passed.
	 * 
	 * @param couponID - the coupon code.
	 * @return boolean value true if the coupon is in stock and the expiration date
	 *         has also not passed or false if no.
	 * @throws CouponSystemException if the check against the database failed.
	 */
	@Override
	public boolean isInventoryAndDateCoupon(int couponID) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select exists(select * from `coupons` where end_date > now() and amount > 0 and id = ?);";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, couponID);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int answer = rs.getInt(1);
			if (answer == 1) {
				return true;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Date and inventory check failed");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return false;
	}

	/**
	 * The method deletes from the coupons table and the purchases table in the
	 * database the coupons whose expiration date has passed.
	 * 
	 * @throws CouponSystemException if the deletion of their coupons and purchases
	 *                               against the database failed.
	 */
	@Override
	public void deleteExpiredCoupons() throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "delete from `coupons` where id in(select id from(select id from `coupons` where end_date < now())as t);";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("Failed to delete expired coupons");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	/**
	 * The method receives from the database of the purchases table the details of
	 * the coupons purchased by the specific selected customer.
	 * 
	 * @param customerID - customer ID number.
	 * @return the list of coupons the customer has purchased with the coupon
	 *         details or an empty list if the customer has not purchased any
	 *         coupons.
	 * @throws CouponSystemException if the operation of getting the customer's
	 *                               coupon list from the database failed.
	 */
	@Override
	public List<Coupon> getCouponsOfCustomer(int customerID) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from `coupons` join `customers_vs_coupons` on coupons.id = customers_vs_coupons.coupon_id where customer_id = ?;";
		List<Coupon> couponsCustomer = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, customerID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyID(rs.getInt("company_id"));
				coupon.setCategory(Category.valueOf(rs.getString("category")));
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStart_date(rs.getDate("start_date"));
				coupon.setEnd_date(rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				couponsCustomer.add(coupon);
			}

		} catch (SQLException e) {
			throw new CouponSystemException("Get coupons of customer failed");

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return couponsCustomer;
	}

	/**
	 * The method receives from the database of the purchases table the details of
	 * the coupons purchased by the customer according to a specific category.
	 * 
	 * @param customerID - customer ID number.
	 * @param category   - the category of the coupon.
	 * @return the list of coupons that the customer purchased with the details of
	 *         the coupons according to the selected category or an empty list if
	 *         the customer did not purchase coupons from this category.
	 * @throws CouponSystemException if the operation of receiving the customer's
	 *                               coupon list by category from the database
	 *                               failed.
	 */
	@Override
	public List<Coupon> getCategoryCouponsOfCustomer(int customerID, Category category) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from `coupons` join `customers_vs_coupons` on coupons.id = customers_vs_coupons.coupon_id where customer_id = ? and  category = ? ;";
		List<Coupon> couponsCategoryCustomer = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, customerID);
			pstmt.setString(2, category.name());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyID(rs.getInt("company_id"));
				coupon.setCategory(Category.valueOf(rs.getString("category")));
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStart_date(rs.getDate("start_date"));
				coupon.setEnd_date(rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				couponsCategoryCustomer.add(coupon);
			}

		} catch (SQLException e) {
			throw new CouponSystemException("Get all coupons of customer from category - failed");

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return couponsCategoryCustomer;
	}

	/**
	 * The method receives from the database of the purchases table the details of
	 * the coupons purchased by the customer up to a selected maximum price.
	 * 
	 * @param customerID - customer ID number.
	 * @param MaxPrice   - maximum price of coupons you wish to receive.
	 * @return the list of coupons that the customer has purchased with the details
	 *         of the coupons up to the selected maximum price, or an empty list if
	 *         the customer has not purchased coupons up to this price.
	 * @throws CouponSystemException if the operation of receiving the customer's
	 *                               coupon list up to a selected maximum price from
	 *                               the database failed.
	 */
	@Override
	public List<Coupon> getCouponsMaxPriceOfCustomer(int customerID, double MaxPrice) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from `coupons` join `customers_vs_coupons` on coupons.id = customers_vs_coupons.coupon_id where customer_id = ? and  price<=? ;";
		List<Coupon> couponsMaxPriceCustomer = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, customerID);
			pstmt.setDouble(2, MaxPrice);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getInt("id"));
				coupon.setCompanyID(rs.getInt("company_id"));
				coupon.setCategory(Category.valueOf(rs.getString("category")));
				coupon.setTitle(rs.getString("title"));
				coupon.setDescription(rs.getString("description"));
				coupon.setStart_date(rs.getDate("start_date"));
				coupon.setEnd_date(rs.getDate("end_date"));
				coupon.setAmount(rs.getInt("amount"));
				coupon.setPrice(rs.getDouble("price"));
				coupon.setImage(rs.getString("image"));
				couponsMaxPriceCustomer.add(coupon);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Get all coupons of customer up to price - failed");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return couponsMaxPriceCustomer;
	}
}

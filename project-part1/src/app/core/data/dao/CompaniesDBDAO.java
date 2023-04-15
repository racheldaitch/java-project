package app.core.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.core.beans.Company;
import app.core.beans.Coupon;
import app.core.data.ConnectionPool;
import app.core.exception.CouponSystemException;

public class CompaniesDBDAO implements CompaniesDAO {

	/**
	 * The method receives a company's email and password, and checks in the
	 * database whether the company exists in the companies table.
	 * 
	 * @param email    - of a company.
	 * @param password - of a company.
	 * @return boolean value does the company exist by email and password.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	@Override
	public boolean isCompanyExists(String email, String password) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select exists(select * from `companies` where (email=?)and(password = ?));";
		try (PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setString(1, email);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int answer = rs.getInt(1);
			if (answer == 1) {
				return true;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Checking whether the company exists by email and password failed");

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

		return false;
	}

	/**
	 * The method receives a company's name and email and checks in the database
	 * whether the company exists in the companies table.
	 * 
	 * @param name  - of a company.
	 * @param email - of a company.
	 * @return boolean value does the company exist by name and email.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	@Override
	public boolean isCompanyNameEmailExists(String name, String email) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select exists(select * from `companies` where (name=?) and (email = ?));";
		try (PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setString(1, name);
			stmt.setString(2, email);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int answer = rs.getInt(1);
			if (answer == 1) {
				return true;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Checking whether the company exists by name and email failed");

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

		return false;
	}

	/**
	 * The method receives a company's code and checks in the database whether the
	 * company exists in the companies table.
	 * 
	 * @param companyID - the company code.
	 * @return boolean value Whether the company exists using the company code.
	 * @throws CouponSystemException if the data check against the database failed.
	 */
	@Override
	public boolean isCompanyIDExists(int companyID) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select exists(select * from `companies` where (id=?));";
		try (PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setInt(1, companyID);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int answer = rs.getInt(1);
			if (answer == 1) {
				return true;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Checking whether the company exists by id failed");

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return false;
	}

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
	@Override
	public boolean isCompanyTitleExists(Coupon coupon) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select exists(select * from `coupons` join `companies` on coupons.company_id = companies.id where title = ? and company_id = ?);";

		try (PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setString(1, coupon.getTitle());
			stmt.setInt(2, coupon.getCompanyID());
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int answer = rs.getInt(1);
			if (answer == 1) {
				return true;
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Checking whether the company exists by title failed");

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

		return false;
	}

	/**
	 * The method receives a company and adds it to the companies table in the
	 * database. and initializes the company code according to the code the company
	 * received in the database.
	 * 
	 * @param company - company details - name, email and password.
	 * @throws CouponSystemException if adding the company to the database companies
	 *                               table failed.
	 */
	@Override
	public void addCompany(Company company) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "insert into `companies` values(0, ?, ?, ?);";
		try (PreparedStatement pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			pstm.setString(1, company.getName());
			pstm.setString(2, company.getEmail());
			pstm.setString(3, company.getPassword());
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			company.setId(id);
			System.out.println("Company " + company.getId() + " has been successfully added");
		} catch (SQLException e) {
			throw new CouponSystemException("Company add failed");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	/**
	 * The method receives a company with updated details, and updates the company
	 * details in the database by the company code.
	 * 
	 * @param company - company details: name, email and password after updating.
	 * @throws CouponSystemException if the company update in the database fails.
	 */
	@Override
	public void updateCompany(Company company) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "update `companies` set name = ?, email = ?, password = ? where id =?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getEmail());
			pstmt.setString(3, company.getPassword());
			pstmt.setInt(4, company.getId());
			pstmt.executeUpdate();
			System.out.println("Company " + company.getId() + " update successful");
		} catch (SQLException e) {
			throw new CouponSystemException("Company update failed");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	/**
	 * The method receives a company code and deletes the company in the database in
	 * the companies table.
	 * 
	 * @param companyID - the company code.
	 * @throws CouponSystemException if the company delete in the database fails.
	 */
	@Override
	public void deleteCompany(int companyID) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "delete from `companies` where id = ?;";
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, companyID);
			pstmt.executeUpdate();
			System.out.println("Company " + companyID + " delete succeeded");
		} catch (SQLException e) {
			throw new CouponSystemException("Company delete failed");

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	/**
	 * The method receives from Data Base the details of the registered companies
	 * from companies's table.
	 * 
	 * @return the list of the companies that exist in the database or an empty list
	 *         if there are no registered companies.
	 * @throws CouponSystemException if the operation of getting the companies list
	 *                               failed.
	 */
	@Override
	public List<Company> getAllCompany() throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from `companies`;";
		List<Company> companies = new ArrayList<>();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Company company = new Company();
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setEmail(rs.getString("email"));
				company.setPassword(rs.getString("password"));
				companies.add(company);
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Get all companies failed");
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return companies;
	}

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
	@Override
	public Company getOneCompany(int companyID) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from `companies` where id = ?;";
		Company company = new Company();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, companyID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				company.setEmail(rs.getString("email"));
				company.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			throw new CouponSystemException("Get one company failed");

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

		return company;
	}

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
	@Override
	public Company getCompany(String email, String password) throws CouponSystemException {
		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "select * from `companies` where (email=?)and(password = ?);";
		Company company = new Company();
		try (PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			company.setId(rs.getInt("id"));
			company.setName(rs.getString("name"));
			company.setEmail(rs.getString("email"));
			company.setPassword(rs.getString("password"));

		} catch (SQLException e) {
			throw new CouponSystemException("Get company failed");

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

		return company;
	}

}

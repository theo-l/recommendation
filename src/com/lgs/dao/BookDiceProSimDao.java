package com.lgs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lgs.bean.BookSim;

public class BookDiceProSimDao extends BaseDao {

	private Statement stmt;

	public BookDiceProSimDao() {
		try {
			this.stmt = this.getConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public double getSimByPairBid(int bid1, int bid2) {
		String sql = "select value from bookdice_prosim where bookid=" + bid1
				+ " and simbookid=" + bid2;
		double value = 0;
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				value = rs.getDouble("value");
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;

	}

	
	//按照聚类阀值获取不同阶段的相似值记录的长度
	public int getLenBySQL(String sql){
		int len=0;
		try {
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				len=rs.getInt("count");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return len;
	}
	
	public List<BookSim> getSimEntitiesBySQL(String sql) {
		List<BookSim> booksim = new ArrayList<BookSim>();

		ResultSet rs;
		try {
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				BookSim bs = new BookSim();
				bs.setId(rs.getInt("id"));
				bs.setBookid(rs.getInt("bookid"));
				bs.setSimbookid(rs.getInt("simbookid"));
				bs.setValue(rs.getDouble("value"));
				booksim.add(bs);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return booksim;
	}

	public int getAllSimCount() {
		int len = 0;
		String sql = "select count(*) count from bookdice_prosim";
		ResultSet rs;
		try {
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				len = rs.getInt("count");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return len;
	}

	// 依次处理书籍相似矩阵中的相似值
	public List<BookSim> getSimEntities(int begin, int offset) {
		List<BookSim> booksim = new ArrayList<BookSim>();

		String sql = "select id,bookid,simbookid,value from bookdice_prosim limit "
				+ begin + "," + offset;
		ResultSet rs;
		try {
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				BookSim bs = new BookSim();
				bs.setId(rs.getInt("id"));
				bs.setBookid(rs.getInt("bookid"));
				bs.setSimbookid(rs.getInt("simbookid"));
				bs.setValue(rs.getDouble("value"));
				booksim.add(bs);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return booksim;
	}

	public List<BookSim> getSimEntitiesByBid(int bid) {
		List<BookSim> related = new ArrayList<BookSim>();
		String sql = "select * from bookdice_prosim where bookid =" + bid;

		try {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				BookSim bs = new BookSim();
				bs.setId(rs.getInt("id"));
				bs.setBookid(rs.getInt("bookid"));
				bs.setSimbookid(rs.getInt("simbookid"));
				bs.setValue(rs.getDouble("value"));
				related.add(bs);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return related;
	}
}

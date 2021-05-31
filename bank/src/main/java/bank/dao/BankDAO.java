package bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bank.vo.Account;

public class BankDAO {
	private static BankDAO dao = new BankDAO();
	private BankDAO() {}
	public static BankDAO getInstance() {
		return dao;
	}
	public Connection connect()
	{
		Connection con = null;
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB","root","1234");
		}catch(Exception e) {
			System.out.println("MemberDAO connetion error"+e);
		}
		return con;
	}
	
	public void close(Connection con, PreparedStatement pstmt) 
	{
		if(pstmt != null) {
			try {
				pstmt.close();
			}catch(Exception e) {
				System.out.println("close error");
			}
		}
		
		if(con != null) {
			try {
				con.close();
			}catch(Exception e) {
				System.out.println("close error");
			}
		}
		
	}
	public void close(Connection con, PreparedStatement pstmt, ResultSet rs) 
	{
		if(rs != null) {
			try {
				rs.close();
			}catch(Exception e) {
				System.out.println("close error");
			}
		}
		
		close(con,pstmt);
		
	}
	
	
	public void join(Account account) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			con = connect();
			pstmt = con.prepareStatement("insert into account values(?,?,?);");
			pstmt.setString(1, account.getId());
			pstmt.setString(2, account.getPwd());
			pstmt.setString(3, account.getMoney()+"");
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("join error" + e);
		}
		finally
		{
			close(con,pstmt);
		}
		
	}
	
	
	public boolean login(String id, String pwd) {
		boolean result = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			con = connect();
			pstmt = con.prepareStatement("select * from account where id = ? and pwd = ?;");
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			
			if(rs.next()) 
			{
				result = true;
			}
			else 
			{
				result = false;
			}
			
		}catch(Exception e) {
			System.out.println("join error" + e);
		}
		finally
		{
			close(con,pstmt,rs);
		}
		
		return result;
	}
	
	
	public int deposit(String id, int money) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int moneyDB = 0;
		try
		{
			con = connect();
			pstmt = con.prepareStatement("select money from account where id = ?;");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) 
			{
				moneyDB = rs.getInt(1);
			}
			
			money = moneyDB + money;
			
			pstmt = con.prepareStatement("update account set money = ? where id = ?;");
			pstmt.setString(1, money+"");
			pstmt.setString(2, id);
			pstmt.executeUpdate();
			
			
			
		}catch(Exception e) {
			System.out.println("deposit error" + e);
		}
		finally
		{
			close(con,pstmt,rs);
		}
		
		return money;
	}
	public int withdrawl(String id, int money) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int moneyDB = 0;
		try
		{
			con = connect();
			pstmt = con.prepareStatement("select money from account where id = ?;");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) 
			{
				moneyDB = rs.getInt(1);
			}
			
			if(money > moneyDB) 
			{
				return -1;
			}
			
			money = moneyDB - money;
			
			pstmt = con.prepareStatement("update account set money = ? where id = ?;");
			pstmt.setString(1, money+"");
			pstmt.setString(2, id);
			pstmt.executeUpdate();
			
			
			
		}catch(Exception e) {
			System.out.println("deposit error" + e);
		}
		finally
		{
			close(con,pstmt,rs);
		}
		
		return money;
	}
	
	
	public int query(String id) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int money = 0;
		try
		{
			con = connect();
			pstmt = con.prepareStatement("select money from account where id = ?;");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) 
			{
				money = rs.getInt(1);
			}
			
			
			
		}catch(Exception e) {
			System.out.println("query error" + e);
		}
		finally
		{
			close(con,pstmt,rs);
		}
		
		return money;
	}
	
	
	public boolean search(String id) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;
		try
		{
			con = connect();
			pstmt = con.prepareStatement("select money from account where id = ?;");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) 
			{
				result = true;
			}
			
			
			
		}catch(Exception e) {
			System.out.println("query error" + e);
		}
		finally
		{
			close(con,pstmt,rs);
		}
		
		return result;
	}
	public int transfer(String id, String rId, int money) {
		// TODO Auto-generated method stub
		int tMoney = this.withdrawl(id, money);
		
		if(tMoney < 0)
		{
			return tMoney;
		}
		this.deposit(rId, money);
		
		
		return tMoney;
	}
	
	
}

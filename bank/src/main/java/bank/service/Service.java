package bank.service;

import bank.dao.BankDAO;
import bank.vo.Account;

public class Service {
	private static Service  service = new Service();
	private Service() {}
	private static BankDAO dao = BankDAO.getInstance();
	public static Service getInstance()
	{
		return service;
	}
	public void join(Account account) {
		// TODO Auto-generated method stub
		dao.join(account);
	}
	public boolean login(String id, String pwd) {
		
		return dao.login(id, pwd);
	}
	public int deposit(String id, int money) {
		// TODO Auto-generated method stub
		return dao.deposit(id, money);
		
	}
	public int withdrawl(String id, int money) {
		return dao.withdrawl(id, money);
	}
	public int query(String id) {
		// TODO Auto-generated method stub
		return dao.query(id);
	}
	public boolean search(String id) {
		// TODO Auto-generated method stub
		return dao.search(id);
	}
	public int transfer(String id, String rId, int money) {
		// TODO Auto-generated method stub
		return dao.transfer(id,rId,money);
	}
}

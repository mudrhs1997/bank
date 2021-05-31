package bank.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bank.service.Service;

public class WithdrawlController implements Controller {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int money = Integer.parseInt(request.getParameter("money"));
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		int totalMoney = Service.getInstance().withdrawl(id, money);
		
		if(totalMoney < 0) 
		{
			request.setAttribute("result", "Money is not enough");
		}
		
			
		request.setAttribute("money", money);
		request.setAttribute("totalMoney", totalMoney);
		HttpUtil.forward(request, response, "/result/withdrawlResult.jsp");

	}

}

package com.daka.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.daka.dao.customer.CustomerDAO;
import com.daka.queue.QueueUtil;
import com.daka.util.Tools;


@Service
public class CustomerTeamService {

	@Autowired
	private CustomerDAO customerDAO;
	
	
	public void takeQueue(){
		try {
			while(true){
				Integer userId = QueueUtil.queue.poll();
				if(null  == userId){
					continue;
				}
				String queryParent = customerDAO.queryParent(userId);
				String parentSql = Tools.getChilds(queryParent.substring(1), "id");
				customerDAO.updateAllParentCount(parentSql);
				queryParent = queryParent.substring(String.valueOf(userId).length()+2);
				String dirParent = queryParent.substring(0,queryParent.indexOf(","));
				customerDAO.updateDirParentCount(String.valueOf(dirParent));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void test(String s){
		String ss =",dsadsa,dsa,dsa,dsa,dsa";
		System.out.println(ss.substring(String.valueOf(s).length()+2));
	}
public static void main(String[] args) {
	String s ="dsadsa";
	test(s);
}
}

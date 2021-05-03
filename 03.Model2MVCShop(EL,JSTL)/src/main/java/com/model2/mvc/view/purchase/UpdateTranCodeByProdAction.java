package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action {//���Ż����ڵ� ������û (����: prodNo)

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< UpdateTranCodeByProdAction : execute() ���� >>>>>");
		System.out.println("prodNo ��? " + request.getParameter("prodNo"));
		System.out.println("tranCode ��? " + request.getParameter("tranCode"));
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
		//listProduct.jsp���� ����ϱ� ��ư Ŭ�� �� 'prodNo' ������
		//updateTranCode()���� 'tranNo'�� �ʿ��ϹǷ� getPurchase2() ����
		Purchase purchase = purchaseService.getPurchase2(Integer.parseInt(request.getParameter("prodNo")));
		System.out.println("���� tranNo : " + purchase.getTranNo());	
		
		//listProduct.jsp���� ����ϱ� ��ư Ŭ�� �� 'tranCode = 2' ������ ==> ����� ����
		purchase.setTranCode(request.getParameter("tranCode"));
		System.out.println("���� tranCode : " + purchase.getTranCode());
		
		purchaseService.updateTranCode(purchase);
		
		System.out.println("<<<<< UpdateTranCodeByProdAction : execute() ���� >>>>>");
		
		return "forward:/listSale.do?menu=manage&page="+request.getParameter("page")+"&tranCode="+request.getParameter("tranCode");
		
	}
}

//System.out.println("prodNo ��? " + request.getParameter("prodNo"));
//System.out.println("tranCode ��? " + request.getParameter("tranCode"));
//
//PurchaseService purchaseService = new PurchaseServiceImpl();
//PurchaseVO purchaseVO1 = new PurchaseVO();
//PurchaseVO purchaseVO2 = new PurchaseVO();
//
////listProduct.jsp���� ����ϱ� ��ư Ŭ�� �� 'prodNo' ������
////updateTranCode()���� 'tranNo'�� �ʿ��ϹǷ� getPurchase2() ����
//purchaseVO1 = purchaseService.getPurchase2(Integer.parseInt(request.getParameter("prodNo")));
//System.out.println("purchaseVO1 �� tranNo ��? " + purchaseVO1.getTranNo());
//System.out.println("purchaseVO1 �� tranCode ��? " + purchaseVO1.getTranCode());
//
////listProduct.jsp���� ����ϱ� ��ư Ŭ�� �� 'tranCode = 2' ������ ==> ����� ����
//purchaseVO2.setTranNo(purchaseVO1.getTranNo());
//purchaseVO2.setTranCode(request.getParameter("tranCode"));
//System.out.println("purchaseVO2 �� tranNo ��? " + purchaseVO2.getTranNo());
//System.out.println("purchaseVO2 �� tranCode ��? " + purchaseVO2.getTranCode());
//
//purchaseService.updateTranCode(purchaseVO2);

























package com.model2.mvc.view.product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddProductAction extends Action {//��ǰ��� ��û

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< AddProductAction : execute() ���� >>>>>");
		
		if(FileUpload.isMultipartContent(request)) {
			//==> ��Ŭ���� workspace / Project ���� �� ������ ��
			String temDir = 
			"C:\\Users\\������\\git\\03.Model2MVCShop\\03.Model2MVCShop(EL,JSTL)\\src\\main\\webapp\\images\\uploadFiles";
			
			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			//setSizeThreshold�� ũ�⸦ ����� �Ǹ� ������ ��ġ�� �ӽ÷� ������
			fileUpload.setSizeMax(1024 * 1024 * 10);
			//�ִ� 1�ް����� ���ε� ���� (1024 * 1024 * 100) <- 100MB
			fileUpload.setSizeThreshold(1024 * 100); //�ѹ��� 100k ������ �޸𸮿� ����
			
			if(request.getContentLength() < fileUpload.getSizeMax()) {
				Product product = new Product();
				
				StringTokenizer token = null;
				
				//parseRequest()�� Fileitem�� �����ϰ� �ִ� List Ÿ���� ������
				List fileItemList = fileUpload.parseRequest(request);
				
				int Size = fileItemList.size(); //html page���� ���� ������ ������ ����
				
				for(int i=0; i<Size; i++) {
					FileItem fileItem = (FileItem) fileItemList.get(i);
					
					//isFormField()�� ���ؼ� ������������ �Ķ�������� ������. �Ķ���͸� true
					if(fileItem.isFormField()) {
						if(fileItem.getFieldName().equals("manuDate")) {
							token = new StringTokenizer(fileItem.getString("euc-kr"), "-");
							String manuDate = token.nextToken() + token.nextToken() + token.nextToken();
							product.setManuDate(manuDate);
						}
						else if(fileItem.getFieldName().equals("prodName")) {
							product.setProdName(fileItem.getString("euc-kr"));
						}
						else if(fileItem.getFieldName().equals("prodDetail")) {
							product.setProdDetail(fileItem.getString("euc-kr"));
						}
						else if(fileItem.getFieldName().equals("price")) {
							product.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
						}
					}else { //���������̸�..
						//out.print("���� : " + fileItem.getFieldName() + "=" + fileItme.getName());
						//out.print("(" + fileItem.getSize() + "byte)<br>");
							
						if(fileItem.getSize() > 0) { //������ �����ϴ� if
							int idx = fileItem.getName().lastIndexOf("\\");
								
							// getName()�� ��θ� �� �������� ������ lastIndexOf�� �߶󳽴�
							if(idx == -1) {
								idx = fileItem.getName().lastIndexOf("/");
							}
							String fileName = fileItem.getName().substring(idx + 1);
							product.setFileName(fileName);
							try {
								File uploadedFile = new File(temDir, fileName);
								fileItem.write(uploadedFile);
							}catch(IOException e) {
								System.out.println(e);
							}
						}else {
							product.setFileName("../../images/empty.GIF");
						}
					}//else ����
				}//for ����
					
				ProductServiceImpl service = new ProductServiceImpl();
				service.addProduct(product);
					
				request.setAttribute("product", product);
					
			}else {// ���ε��ϴ� ������ setSizeMax ���� ū ���
				int overSize = (request.getContentLength() / 1000000);
				System.out.println("<script>alert('������ ũ��� 1MB���� �Դϴ�. �ø��� ���� �뷮��"
						+ overSize + "MB�Դϴ�.');");
				System.out.println("history.back();</script>");					
			}
		}else {
			System.out.println("���ڵ� Ÿ���� multipart/form-data�� �ƴմϴ�..");
		}
		
		return "forward:/product/addProduct.jsp";
		
///////////////////////////////// ���� �ڵ� ////////////////////////////////////////////////////////		
//		Product product = new Product();
//		product.setProdName(request.getParameter("prodName"));                    //��ǰ��
//		product.setProdDetail(request.getParameter("prodDetail"));                //��ǰ������
//		
//		//���1
//		product.setManuDate(request.getParameter("manuDate").replaceAll("-", ""));//��������
//		
//		//���2
////		String manuDay = "";
////		for( String temp : request.getParameter("manuDate").split("-")) {
////			manuDay += temp;
////		}			
////		product.setManuDate(manuDay);
//		
//		product.setPrice(Integer.parseInt(request.getParameter("price")));        //����		
//		product.setFileName(request.getParameter("fileName"));                    //��ǰ�̹���
//		
//		System.out.println("product ���ÿϷ� : " + product);
//		
//		ProductService service = new ProductServiceImpl();
//		service.addProduct(product);
//		
//		//���1
//		request.setAttribute("product", product);
//		
//		//���2
////		HttpSession session = request.getSession(true);		
////		session.setAttribute("productVO",productVO);
//		
//		System.out.println("<<<<< AddProductAction : execute() ���� >>>>>");
//		
//		return "forward:/product/addProduct.jsp";	
////////////////////////////////////////////////////////////////////////////////////////////////		
	
	}//end of execute()
}//end of class

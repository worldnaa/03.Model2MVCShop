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

public class AddProductAction extends Action {//상품등록 요청

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< AddProductAction : execute() 시작 >>>>>");
		
		if(FileUpload.isMultipartContent(request)) {
			//==> 이클립스 workspace / Project 변경 시 변경할 것
			String temDir = 
			"C:\\Users\\이유리\\git\\03.Model2MVCShop\\03.Model2MVCShop(EL,JSTL)\\src\\main\\webapp\\images\\uploadFiles";
			
			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			//setSizeThreshold의 크기를 벗어나게 되면 지정한 위치에 임시로 저장함
			fileUpload.setSizeMax(1024 * 1024 * 10);
			//최대 1메가까지 업로드 가능 (1024 * 1024 * 100) <- 100MB
			fileUpload.setSizeThreshold(1024 * 100); //한번에 100k 까지는 메모리에 저장
			
			if(request.getContentLength() < fileUpload.getSizeMax()) {
				Product product = new Product();
				
				StringTokenizer token = null;
				
				//parseRequest()는 Fileitem을 포함하고 있는 List 타입을 리턴함
				List fileItemList = fileUpload.parseRequest(request);
				
				int Size = fileItemList.size(); //html page에서 받은 값들의 개수를 구함
				
				for(int i=0; i<Size; i++) {
					FileItem fileItem = (FileItem) fileItemList.get(i);
					
					//isFormField()를 통해서 파일형식인지 파라미터인지 구분함. 파라미터면 true
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
					}else { //파일형식이면..
						//out.print("파일 : " + fileItem.getFieldName() + "=" + fileItme.getName());
						//out.print("(" + fileItem.getSize() + "byte)<br>");
							
						if(fileItem.getSize() > 0) { //파일을 저장하는 if
							int idx = fileItem.getName().lastIndexOf("\\");
								
							// getName()은 경로를 다 가져오기 때문에 lastIndexOf로 잘라낸다
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
					}//else 종료
				}//for 종료
					
				ProductServiceImpl service = new ProductServiceImpl();
				service.addProduct(product);
					
				request.setAttribute("product", product);
					
			}else {// 업로드하는 파일이 setSizeMax 보다 큰 경우
				int overSize = (request.getContentLength() / 1000000);
				System.out.println("<script>alert('파일의 크기는 1MB까지 입니다. 올리신 파일 용량은"
						+ overSize + "MB입니다.');");
				System.out.println("history.back();</script>");					
			}
		}else {
			System.out.println("인코딩 타입이 multipart/form-data가 아닙니다..");
		}
		
		return "forward:/product/addProduct.jsp";
		
///////////////////////////////// 기존 코드 ////////////////////////////////////////////////////////		
//		Product product = new Product();
//		product.setProdName(request.getParameter("prodName"));                    //상품명
//		product.setProdDetail(request.getParameter("prodDetail"));                //상품상세정보
//		
//		//방법1
//		product.setManuDate(request.getParameter("manuDate").replaceAll("-", ""));//제조일자
//		
//		//방법2
////		String manuDay = "";
////		for( String temp : request.getParameter("manuDate").split("-")) {
////			manuDay += temp;
////		}			
////		product.setManuDate(manuDay);
//		
//		product.setPrice(Integer.parseInt(request.getParameter("price")));        //가격		
//		product.setFileName(request.getParameter("fileName"));                    //상품이미지
//		
//		System.out.println("product 셋팅완료 : " + product);
//		
//		ProductService service = new ProductServiceImpl();
//		service.addProduct(product);
//		
//		//방법1
//		request.setAttribute("product", product);
//		
//		//방법2
////		HttpSession session = request.getSession(true);		
////		session.setAttribute("productVO",productVO);
//		
//		System.out.println("<<<<< AddProductAction : execute() 종료 >>>>>");
//		
//		return "forward:/product/addProduct.jsp";	
////////////////////////////////////////////////////////////////////////////////////////////////		
	
	}//end of execute()
}//end of class

<%@page contentType="text/html; charset=euc-kr" %>

<%@page import="java.util.List"%>

<%@page import="com.model2.mvc.service.domain.*"%>
<%@page import="com.model2.mvc.common.Search"%>
<%@page import="com.model2.mvc.common.Page"%>
<%@page import="com.model2.mvc.common.util.CommonUtil"%>

<%
	System.out.println("<<<<< listProduct.jsp ���� >>>>>");

	List<Product> list = (List<Product>)request.getAttribute("list");
	System.out.println("���� list : " + list);
	
	Search search = (Search)request.getAttribute("search");
	System.out.println("���� search : " + search);
	
	Page resultPage = (Page)request.getAttribute("resultPage");
	System.out.println("���� resultPage : " + resultPage);
	
	//==> null �� ""(nullString)���� ����
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
		
	System.out.println("searchCondition��? " + searchCondition);
	System.out.println("searchKeyword��? " + searchKeyword);
%>


<html>
<head>
<title>��ǰ �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	//�˻� / page �ΰ��� ��� ��� Form ������ ���� JavaScrpt �̿�
	function fncGetProductList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}
</script>

</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?menu=search" method="post">


<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
						��ǰ �����ȸ
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" <%= (searchCondition.equals("0") ? "selected" : "")%>>��ǰ��ȣ</option>
				<option value="1" <%= (searchCondition.equals("1") ? "selected" : "")%>>��ǰ��</option>
				<option value="2" <%= (searchCondition.equals("2") ? "selected" : "")%>>��ǰ����</option>
			</select>
			<input type="text" name="searchKeyword" value="<%= searchKeyword %>" 
				   class="ct_input_g" style="width:200px; height:19px" >
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetProductList('<%= resultPage.getCurrentPage() %>');">�˻�</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >
			��ü <%= resultPage.getTotalCount() %> �Ǽ�, ���� <%= resultPage.getCurrentPage() %> ������
		</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">�������</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<% 	
		for(int i=0; i<list.size(); i++) {
			Product vo = list.get(i);
	%>
	<tr class="ct_list_pop">
		<td align="center"><%= i + 1 %></td>
		<td></td>
		<td align="left">
		
		<% if(vo.getProTranCode().equals("�Ǹ���")) {%>
				<a href="/getProduct.do?prodNo=<%=vo.getProdNo()%>&menu=search"><%=vo.getProdName()%></a>
		<% }else{%>
			<%=vo.getProdName()%>
		<% } %>
		
		</td>
		<td></td>
		<td align="left"><%= vo.getPrice() %></td>
		<td></td>
		<td align="left"><%= vo.getRegDate() %></td>
		<td></td>
		<td align="left"><%= vo.getProTranCode() %></td>	
	</tr>			
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	<% } %>
</table>	
			
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
			<input type="hidden" id="currentPage" name="currentPage" value=""/>
			<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					�� ����
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getCurrentPage()-1%>')">�� ����</a>
			<% } %>
			
			<% for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++) { %>
				<a href="javascript:fncGetProductList('<%=i%>')"><%=i%></a>
			<% } %>
			
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					���� ��
			<% }else{ %>
					<a href="javascript:fncGetProductList('<%=resultPage.getEndUnitPage()+1%>')">���� ��</a>
			<% } %>
    	</td>
	</tr>
</table>
<!--  ������ Navigator �� -->

</form>

</div>
</body>
</html>
<% System.out.println("<<<<< listProduct.jsp ���� >>>>>"); %>
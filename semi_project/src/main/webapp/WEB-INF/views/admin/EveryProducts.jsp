<%-- <%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.List, com.web.admin.product.dto.AddProduct" %>

		<table>
            	<tr>
            		<th>����</th>
            		<th>�귣��</th>
            		<th>��ǰ��</th>
            		<th>����</th>
            		<th>������</th>
            		<th>����</th>
            	</tr>
            	<% String category = "";
            		for(AddProduct p : products){ %>
            	<tr>
            		<td>
            		<% switch(p.getCategory()){ 
	            		case 1: category="���"; break;
	            		case 2: category="����"; break;
	            		case 3: category="�躯�е�"; break;
	            		case 4: category="�Ƿ�"; break;
	            		case 5: category="����ǰ"; break;
	            		case 6: category="�̿��ǰ"; break;
	            		case 7: category="�ϳ׽�/������"; break;
	            		case 8: category="��Ÿ"; break;
	            		default: category="�߸��� �з�"; break;
            		}%>
            		<%=category %>
            		</td>
            		<td><%=p.getCategory() %></td>
            		<td><%=p.getProductName() %></td>
            		<td><%=p.getPrice() %></td>
            		<td><%=p.getDiscount() %></td>
            		<td><button class="deleteProductBtn" value="<%=p.getProductKey()%>">����</button></td>
            	</tr>
            	<%} %>
            </table>
            <div>
	        	<%=request.getAttribute("pageBar") %>
	        </div> --%>
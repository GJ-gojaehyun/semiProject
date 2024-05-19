package com.web.shoppingmall.model.dao;

import static com.web.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.web.shoppingmall.model.dto.Product;
import com.web.shoppingmall.model.dto.ProductCategory;
import com.web.shoppingmall.model.dto.ProductImg;

/*
 * 쇼핑몰 dao
 */
public class ShoppingmallDao {
	private static ShoppingmallDao dao = new ShoppingmallDao();
	public static ShoppingmallDao getDao() {return dao;}
	private Properties sql = new Properties();
	
	private ShoppingmallDao(){
		String path = ShoppingmallDao.class.getResource("/sql/shoppingmall/sql_shoppingmall.properties").getPath();
		try(FileReader fr=  new FileReader(path)){
			sql.load(fr);
		}catch(IOException e) {
			System.out.println("shoppingmall_properties 읽어오는 중 오류 발생");
			e.printStackTrace();
		}
	}
	
	/*
	 * 	매개변수로 받은 카테고리의 상풍 총 개수를 반환하는 메소드
	 * 	매개변수 : 카테고리번호
	 * 	반환 : 카테고리 상품의 총 개수(int)
	 */
	public int AllProductCount(Connection conn, int category) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("allProductCount"));
			pstmt.setInt(1, category);
			rs=pstmt.executeQuery();
			if(rs.next()) result=rs.getInt(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return result;
	}
	
	/*
	 * 	매개변수로 받은 페이지와 카테고리와 정렬기준에 맞는 상품들의 정보를 가져오는 메소드
	 * 	매개변수 : 카테고리넘버, 현재페이지숫자, 출력할상품개수, 정렬기준
	 * 	반환 : 상품 리스트
	 */
	public List<Product> selectProduct(Connection conn, int category, int cPage, int numPerpage, String sort){
		List<Product> result=new ArrayList<Product>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sortSql=sql.getProperty("selectProduct").replace(":SORT", sort);
		try {
			pstmt=conn.prepareStatement(sortSql);
			pstmt.setInt(1, category);
			pstmt.setInt(2, (cPage-1)*numPerpage+1);
			pstmt.setInt(3, cPage*numPerpage);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				result.add(getProductForListpage(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return result;
	}
	
	/*
	 * 	쇼핑몰 상품 상세페이지에 필요한 상품정보를 가져오는 메소드
	 * 	상품의 고유키로 상품을 검색하여 가져온다.
	 * 	매개변수 : 상품고유키
	 * 	반환 : 상품 객체
	 */
	public Product selectProductByKey(Connection conn, int productKey) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Product result=null;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectProductByKey"));
			pstmt.setInt(1, productKey);
			rs=pstmt.executeQuery();
			if(rs.next())
				result=getProductForDetailpage(rs, result);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return result;
	}
	
	
	
	
	
	
	
	
	
	
	/*
	 * 	쇼핑몰 리스트 페이지에 필요한 상품의 정보만 담아서 Product 객체를 반환하는 메소드
	 * 	상품의 이름, 가격, 할인율, 리뷰총개수, 평균별점, 이미지, 카테고리 정보가 포함되어 있음
	 * 	매개변수 : ResultSet
	 * 	반환 : 상품 객체
	 */
	private Product getProductForListpage(ResultSet rs) throws SQLException{
		Map<String, ProductImg> imgs=new HashMap<>();
		imgs.put("thumbnail", ProductImg.builder().productImg(rs.getString("PRODUCT_IMG")).build());
		return new Product().builder()
				.productName(rs.getString("PRODUCT_NAME"))
				.price(rs.getInt("PRICE"))
				.rateDiscount(rs.getInt("RATE_DISCOUNT"))
				.totalReviewCount(rs.getInt("R_COUNT"))
				.avgRating(rs.getDouble("AVG_RATING"))
				.productImgs(imgs)
				.productCategory(ProductCategory.builder().productCategoryName(rs.getString("PRODUCT_CATEGORY_NAME")).build())
				.build();
	}
	
	/*
	 * 	쇼핑몰 상품 상세페이지에 필요한 상품 정보를 담아 반환하는 메소드
	 * 	상품의 이름, 가격, 할인율, 총리뷰개수, 평균별점, 이미지이름 정보가 포함되어 있음
	 * 	매개변수 : ResultSet
	 * 	반환 : 상품 객체
	 */
	private Product getProductForDetailpage(ResultSet rs, Product p) throws SQLException{
		p=new Product().builder()
				.productName(rs.getString("PRODUCT_NAME"))
				.price(rs.getInt("PRICE"))
				.rateDiscount(rs.getInt("RATE_DISCOUNT"))
				.totalReviewCount(rs.getInt("R_COUNT"))
				.avgRating(rs.getDouble("AVG_RATING"))
				
				.build();
		Map<String, ProductImg> imgs=new HashMap<>();
		int str=1;
		do{
			if(rs.getString("THUMBNAIL").equals("Y")) {
				imgs.put("thumbnail", ProductImg.builder().productImg(rs.getString("PRODUCT_IMG")).build());
			}else if(rs.getString("DESCRIPTION_IMG").equals("Y")) {
				imgs.put("description", ProductImg.builder().productImg(rs.getString("PRODUCT_IMG")).build());
			}else {
				imgs.put("img"+str, ProductImg.builder().productImg(rs.getString("PRODUCT_IMG")).build());
				str++;
			}
		}while(rs.next());
		p.setProductImgs(imgs);
		return p;
	}
}

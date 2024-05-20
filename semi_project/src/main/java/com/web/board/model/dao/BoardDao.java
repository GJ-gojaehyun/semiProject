package com.web.board.model.dao;

import static com.web.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.web.board.model.dto.Bulletin;
import com.web.board.model.dto.BulletinComment;
import com.web.dog.model.dao.DogDao;
import com.web.dog.model.dto.Dog;

public class BoardDao {
	//싱글톤 적용
	private static BoardDao dao = new BoardDao();
	public static BoardDao getDao() {return dao;}
	private Properties sql = new Properties();
	
	//기본 생성자 안에 properties 넣기
	private BoardDao(){
		String path = BoardDao.class.getResource("/sql/board/sql_board.properties").getPath();
		try(FileReader fr=  new FileReader(path)){
			sql.load(fr);
		}catch(IOException e) {
			System.out.println("board_properties 읽어오는 중 오류 발생");
			e.printStackTrace();
		}
	}
	
	//게시글 총 갯수 조회
	public int selectBoardCount(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectBoardCount"));
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	
	//게시글 전체 조회
	public List<Bulletin> selectBoardAll(Connection conn, int cPage, int numPerpage, String type, String keyword){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Bulletin> bulletins = new ArrayList<>();
		try {
			String newSql = sql.getProperty("selectBoardAll").replace("#type", type);
			pstmt = conn.prepareStatement(newSql);
			pstmt.setString(1,"%"+keyword+"%");
			pstmt.setInt(2,(cPage-1)*numPerpage+1);
			pstmt.setInt(3, numPerpage*cPage);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				bulletins.add(getBulletin(rs));
			}
		}catch(SQLException e) {
			System.out.println("BoardDao_selectBoardAll에서 SQL 오류");
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return bulletins;
	}
	
	//게시글 번호로 조회
	public Bulletin selectBoardNo(Connection conn, int no) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Bulletin bulletin  = null;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectBoardNo"));
			pstmt.setInt(1,no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bulletin = getBulletin(rs);
				do {
					bulletin.getComments().add(getComments(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return bulletin;
	}
	
	//게시글 등록하기
	public int insertBoard(Connection conn, String id, String title, String content) {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("insertBoard"));
			pstmt.setString(1,id);
			pstmt.setString(2, title);
			pstmt.setString(3, content);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	//게시글 수정하기
	public int updateFreeBoard(Connection conn, int bullNo, String content) {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("updateFreeBoard"));
			pstmt.setString(1, content);
			pstmt.setInt(2, bullNo);
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	//게시글 삭제하기
	public int deleteFreeBoard(Connection conn, int bullNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt= conn.prepareStatement(sql.getProperty("deleteFreeBoard"));
			pstmt.setInt(1, bullNo);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	//강아지 불러오기
	public List<Dog> getDog(Connection conn){
		List<Dog> dogs = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("getDog"));
			rs=pstmt.executeQuery();
			while(rs.next()) {
				dogs.add(DogDao.getDog(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return dogs;
	}
	
	//게시글 조회 수 증가
	public int updateFreeBoardReadCount(Connection conn, int no) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("updateFreeBoardReadCount"));
			pstmt.setInt(1, no);
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	
	//댓글 등록하기
	public int insertBoardComment(Connection conn, BulletinComment bc) {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("insertBoardComment"));
			pstmt.setInt(1, bc.getBullNo());
			pstmt.setString(2,bc.getUserId());
			pstmt.setString(3,bc.getContent());
			pstmt.setInt(4,bc.getCommentLevel());
			result= pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	//댓글 삭제하기
	public int deleteBoardComment(Connection conn, int bcNo) {
		PreparedStatement pstmt =null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("deleteBoardComment"));
			pstmt.setInt(1, bcNo);
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	private static BulletinComment getComments(ResultSet rs) throws SQLException{
			 String delCStr = rs.getString("bc_del_c");
			 char delC = (delCStr != null && !delCStr.isEmpty()) ? delCStr.charAt(0) : 'N';
		return BulletinComment.builder()
				.mainComment(rs.getInt("main_comment"))
				.subComment(rs.getInt("sub_comment"))
				.bullNo(rs.getInt("bc_bull_no"))
				.userId(rs.getString("bc_user_id"))
				.content(rs.getString("bc_content"))
				.rDate(rs.getDate("bc_r_date"))
				.delC(delC)
				.commentLevel(rs.getInt("comment_level"))
				.build();
	}
	private static Bulletin getBulletin(ResultSet rs) throws SQLException{
		return Bulletin.builder()
				.bullNo(rs.getInt("b_bull_no"))
				.categoryNo(rs.getInt("category_no"))
				.userId(rs.getString("b_user_id"))
				.title(rs.getString("b_title"))
				.content(rs.getString("b_content"))
				.rDate(rs.getDate("b_r_date"))
				.hits(rs.getInt("hits"))
				.likeC(rs.getInt("like_c"))
				.comments(new ArrayList<>())
				.build();
	}
}

package com.one.whospet.dao.shop.face;

import java.util.ArrayList;
import java.util.List;

import com.one.whospet.dto.Order;
import com.one.whospet.dto.Payment;
import com.one.whospet.dto.Shop;
import com.one.whospet.dto.ShopImg;
import com.one.whospet.dto.User;
import com.one.whospet.util.ShopPaging;

public interface ShopDao {

	/**
	 * 전체 게시글 수를 조회한다
	 * 
	 * @return 총 게시글 수
	 */
	public int selectCntAll(ShopPaging inData);

	
	
	/**
	 * 페이징 적용된 전체 목록 게시글 조회
	 * 
	 * @param paging 페이징 정보 객체
	 * @return 페이징이 적용된 게시글 리스트
	 */
	public List<Shop> selectPageList(ShopPaging paging);


	/**
	 * 상품번호로 상품정보 조회
	 * 
	 * @param shop 상품번호를 가진 객체
	 * @return 상품정보객체
	 */
	public Shop selectShopBySno(Shop viewShop);

	/**
	 * 상품번호를 이용해 해당 상품번호에 해당하는 첨부파일 찾기
	 * 
	 * @param sNo 상품번호
	 * @return 상품번호와 일치하는 상품이미지테이블의 정보 
	 */
	public List<ShopImg> selectShopImgBySNo(int sNo);

	/**
	 * 상품번호를 이용해 해당 상품번호에 해당하는 섬네일 파일 찾기
	 * 
	 * @param sNo 상품번호
	 * @return 상품번호와 일치하는 썸네일파일의 정보
	 */
	public ShopImg selectThumbnailBySNo(int sNo);

	/**
	 * 사용자 상품목록의 
	 * 
	 * @param sNo 사용자 상품목록의 상품번호들
	 */
	public ShopImg selectThumbnailsBySNo(int i);

	/**
	 * 상품정보 삽입하기
	 * 
	 * @param shop 상품정보객체
	 */
	public void insertShop(Shop shop);


	/**
	 * 첨부파일 정보 삽입하기
	 * 
	 * @param shopImg 첨부파일 정보
	 */
	public void insertFile(ShopImg shopImg);


	/**
	 * 상품의 수정정보를 DB에 업데이트하기
	 * 
	 * @param shop 상품 수정정보
	 */
	public void updateShopInfo(Shop shop);


	/**
	 * delFileList안의 siNo로 DB에서 파일 삭제하기
	 * 
	 * @param delFileList siNo 데이터 내포
	 */
	public void deleteFileBySiNo(int delFileList);

	
	/**
	 * shop안에 sNo로 DB에서 상품이미지 삭제하기
	 * 
	 * @param shop sNo 데이터 내포
	 */
	public void deleteShopImgBySNo(Shop shop);

	
	/**
	 * shop안의 sNo로 DB에서 상품 삭제하기
	 * 
	 * @param shop sNo 데이터 내포
	 */
	public void deleteShopBySNo(Shop shop);


	/**
	 * 장바구니에 넣고자 하는 수량 + 상품정보 를 장바구니 DB에 삽입하기
	 * 
	 * @param basketInfo 장바구니 정보
	 */
	public int insertBasket(Shop basketInfo);


	/**
	 * 유저정보에 맞는 유저정보 조회하기
	 * 
	 * @param uNo 유저정보
	 * @return 유저번호에 맞는 유저정보
	 */
	public User selectUserInfo(int uNo);


	/**
	 * 추가할 결제정보 삽입하기
	 * 
	 * @param payment 추가할 결제정보
	 */
	public void insertPayment(Payment payment);


	/**
	 * 추가할 주문정보 삽입하기
	 * 
	 * @param orderdata 추가할 주문정보
	 */
	public void insertOrder(Order orderdata);
	
	
	/**
	 * 유저번호로 결제테이블에서 결제정보 조회하기
	 * 
	 * @param uNo
	 * @return 결제정보
	 */
	public Payment selectPyNo(int uNo);


	/**
	 * 유저번호로 주문테이블에서 주문정보 조회하기
	 * 
	 * @param uNo 유저번호
	 * @return 
	 */
	public Order selectOrder(int pyNo);


	/**
	 * 결제번호로 결제테이블에서 결제정보 조회하기
	 * 
	 * @param pyNo 결제번호
	 * @return 결제정보
	 */
	public Payment selectPayment(int pyNo);


	/**
	 * 상품번호로 상품정보 조회하기
	 * 
	 * @param sNo 상품번호
	 * @return 상품정보
	 */
	public Shop selectShop(int sNo);



	/**
	 * sNo으로 장바구니에 상품이 있는지 체크
	 * 
	 * @param basketInfo sNo이 담긴 장바구니정보
	 * @return 있으면 1 없으면 0
	 */
	public int selectCntSNoInBasket(Shop basketInfo);


	/**
	 * 장바구니 정보 수정하기
	 * 
	 * @param basketInfo 장바구니 정보
	 */
	public int updateBasket(Shop basketInfo);


	/**
	 * 장바구니 정보 삭제하기
	 * 
	 * @param sNo 상품번호
	 */
	public void deleteBasket(int sNo);



	public void updatePayment(String imp_uid);

	
	



	

	




}

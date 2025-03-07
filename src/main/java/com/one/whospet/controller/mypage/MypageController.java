package com.one.whospet.controller.mypage;

import java.util.HashMap;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.one.whospet.dto.Board;
import com.one.whospet.dto.Booking;
import com.one.whospet.dto.Hospital;
import com.one.whospet.dto.Order;
import com.one.whospet.dto.Point;
import com.one.whospet.dto.ShopBasket;
import com.one.whospet.dto.User;
import com.one.whospet.dto.Userpic;
import com.one.whospet.service.mypage.face.MypageService;
import com.one.whospet.util.MypageBoardPaging;





@Controller
public class MypageController {
	//로거 객체
	private static final Logger logger = LoggerFactory.getLogger(MypageController.class);
	//로그인 서비스 객체
	@Autowired
	private MypageService mypageService;
	//메일 보내는 객체
	@Autowired 
	private JavaMailSenderImpl mailSender;
	
	
	@RequestMapping(value = "/mypage/main")
	public void main() {}
	
	//유저 정보
	@RequestMapping(value = "/mypage/user")
	public void userinfo(HttpSession session, Model model) {
		
		User user = (User) session.getAttribute("user");
		User uinfo = mypageService.getUserInfo(user);
		Userpic upic = (Userpic) mypageService.getUserpic(user);
		//모델값 전달
		model.addAttribute("uinfo", uinfo);
		model.addAttribute("upic", upic);
	}
	
	//유저 프로필 사진 등록 화면
	@RequestMapping(value = "/mypage/userpic", method=RequestMethod.GET)
	public void userpic() {}
	
	//유저 프로필 사진 등록 처리
	@RequestMapping(value = "/mypage/userpic", method=RequestMethod.POST)
	public String userpicProc(Userpic userpic, HttpSession session, @RequestParam("file") MultipartFile file) {
		
		logger.debug("file정보" + file.toString());
		User user = (User) session.getAttribute("user");
		
		mypageService.deletePic(user);
		
		mypageService.filesave(user, file);
		return "redirect: /mypage/info";
	}
	
	//유저 프로필 사진 삭제 처리
	@RequestMapping(value = "/mypage/userpicDelete", method=RequestMethod.POST)
	public String userpicDeleteProc(Userpic userpic, HttpSession session, @RequestParam("file") MultipartFile file) {
		
		logger.debug("file정보" + file.toString());
		User user = (User) session.getAttribute("user");
		
		mypageService.deletePic(user);
		
		return "redirect: /mypage/info";
	}
	
	//처리 완료 창
	@RequestMapping(value = "/mypage/info")
	public void done() {
		
		 
	}
	
	//회원 정보 수정전 확인 창
	@RequestMapping(value = "/mypage/updateCk", method=RequestMethod.GET)
	public void updateCk(HttpSession session, Model model) {
		
	}
	//회원 정보 수정 창
	@RequestMapping(value = "/mypage/update", method=RequestMethod.GET)
	public void update(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		User uinfo = mypageService.getUserInfo(user);
		//모델값 전달
		model.addAttribute("uinfo", uinfo);
	}
	
	//회원 정보 수정 처리
	@RequestMapping(value = "/mypage/update", method=RequestMethod.POST)
	public String updateProc(User upuser) {
		mypageService.update(upuser);
		return "redirect: /mypage/info";
	}
	
	//회원 탈퇴 페이지
	@RequestMapping(value = "/mypage/userout", method=RequestMethod.GET)
	public void deleteUser(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		User uinfo = mypageService.getUserInfo(user);
		//모델값 전달
		model.addAttribute("uinfo", uinfo);
	}
	
	//회원 탈퇴 처리
	@RequestMapping(value = "/mypage/userout", method=RequestMethod.POST)
	public String deleteUserProc(User outuser, HttpSession session) {
		mypageService.out(outuser, session);
		return "/mypage/info";
	}
	
	//게시판 목록 가져오기
	@RequestMapping(value = "/mypage/board")
	public void boardinfo(@RequestParam(defaultValue = "0") int curPage, HttpSession session, Model model) {
		
		//해쉬맵 생성
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		logger.info("페이징 객체 가져오는지 확인해보기~~~!!! : {}", curPage);
		//유저 번호 가져오기
		User user = (User) session.getAttribute("user");
		int uNo = user.getuNo();
//		int curPage = curpage.getCurPage();
		
		//해쉬맵에 집어넣기
		data.put("uNo", uNo);
		data.put("curPage", curPage);
		
		//페이징 계산
		MypageBoardPaging paging = mypageService.getBoardPaging(data);
		
		//페이징 객체 집어넣기
		data.put("paging", paging);
		logger.info("data 객체 가져오는지 확인해보기~~~!!! : {}", data);
		//유저번호에 따른 게시글 가져오기 서비스호출
		List<Board> ublist = mypageService.getBoardByUser(data);
		for( int i=0; i<ublist.size(); i++) {
			logger.info("게시글 목록" + ublist.get(i).toString());
			}
		//모델값 전달
		model.addAttribute("ublist", ublist);
		model.addAttribute("paging", paging);
	}
	
	//예약정보 가져오기
	@RequestMapping(value = "/mypage/booking")
	public void bookinginfo(@RequestParam(defaultValue = "0") int curPage, HttpSession session, Model model) {
		
		//해쉬맵 생성
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		//유저 번호 가져오기
		User user = (User) session.getAttribute("user");
		int uNo = user.getuNo();
		
		//해쉬맵에 집어넣기
		data.put("uNo", uNo);
		data.put("curPage", curPage);
		
		//페이징 계산
		MypageBoardPaging paging = mypageService.getBookingPaging(data);
		
		//페이징 객체 집어넣기
		data.put("paging", paging);
		
		logger.info("data 객체 가져오는지 확인해보기~~~!!! : {}", data);
		//유저번호에 따른 게시글 가져오기 서비스호출
		List<Booking> booklist = mypageService.getBookingByUser(data);
		for( int i=0; i<booklist.size(); i++) {
			logger.info("게시글 목록" + booklist.get(i).toString());
			}
		
		//모델값 전달
		model.addAttribute("booklist", booklist);
		model.addAttribute("paging", paging);
	}
	
	//예약정보 상세보기 가져오기
	@RequestMapping(value = "/mypage/bookingDetail", method=RequestMethod.GET)
	public void bookingdetail(int bookno, Model model) {
		Booking view = mypageService.view(bookno);
		//model에 첨부파일 속성값 설정
		model.addAttribute("view", view);
		
	}
	
	//예약 취소 처리
	@RequestMapping(value = "/mypage/bookingDetail", method=RequestMethod.POST)
	public String bookingcancel(Booking booking, HttpSession session) {
		//유저아이디와 예약정보 가져오고 저장
		User user = (User) session.getAttribute("user");
		final String uId = user.getuId();
		final int bookno = booking.getBookNo();
		
		logger.info("booking객체 확인" + booking.toString());
		mypageService.bookingCancel(booking);
		
		

		
		// 메일 전송 객체 생성 
		final MimeMessagePreparator preparator = new MimeMessagePreparator() {
		@Override public void prepare(MimeMessage mimeMessage) throws Exception { 
		final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
						
						
			helper.setFrom("WhosPet <kul32137@gmail.com>"); // 보내는 사람 <> 이메일 주소
			helper.setTo("kul321japan@gmail.com");  // 받는 사람	
			helper.setSubject( uId + "님의 예약이 취소되었습니다"); // 제목 
			helper.setText("예약번호"+bookno+"번의 예약이 취소되었습니다", true); //내용
					} 
					
				}; 
				mailSender.send(preparator); //메일을 보낸다.
//		
		//마이페이지 메인으로 리다이렉트
		return "redirect:/mypage/user";
		
		
	}
	
	//포인트 이력 가져오기
	@RequestMapping(value = "/mypage/point")
	public void pointinfo(@RequestParam(defaultValue = "0") int curPage, HttpSession session, Model model) {
		//해쉬맵 생성
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		//유저 번호 가져오기
		User user = (User) session.getAttribute("user");
		int uNo = user.getuNo();
		
		//해쉬맵에 집어넣기
		data.put("uNo", uNo);
		data.put("curPage", curPage);
		
		//페이징 계산
		MypageBoardPaging paging = mypageService.getPointPaging(data);
		
		//페이징 객체 집어넣기
		data.put("paging", paging);
		
		logger.info("data 객체 가져오는지 확인해보기~~~!!! : {}", data);
		
		//유저번호에 따른 포인트이력 가져오기 서비스호출
		List<Point> pointlist = mypageService.getPointByUser(data);
		for( int i=0; i<pointlist.size(); i++) {
			logger.info("포인트 이력" + pointlist.get(i).toString());
			}
		
		//현재포인트(지금까지의 유저 총합포인트) 구하기
		Point curpoint = mypageService.getCurpointByUser(uNo);
		
		//모델값 전달
		model.addAttribute("pointlist", pointlist);
		model.addAttribute("paging", paging);
		model.addAttribute("curpoint", curpoint);
	
		
	}

	
	//장바구니 페이지
	@RequestMapping(value = "/mypage/basket")
	public void basketinfo(@RequestParam(defaultValue = "0") int curPage, HttpSession session, Model model) {
		
		//해쉬맵 생성
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		//유저 번호 가져오기
		User user = (User) session.getAttribute("user");
		int uNo = user.getuNo();
		
		//해쉬맵에 집어넣기
		data.put("uNo", uNo);
		data.put("curPage", curPage);
		
		//페이징 계산
		MypageBoardPaging paging = mypageService.getBasketPaging(data);
		//페이징 객체 집어넣기
		data.put("paging", paging);
		//장바구니 목록 조회
		List<ShopBasket> list = mypageService.basketList(data);
		for( int i=0; i<list.size(); i++) {
		logger.debug(list.get(i).toString());
		}
		

		Integer sumbasket = mypageService.basketSum(data);
		model.addAttribute("sum", sumbasket);
		
		
		//모델값 전달
		model.addAttribute("basketlist",list);
		model.addAttribute("paging", paging);
	}
	
	
	
	//장바구니 삭제
	@RequestMapping(value = "/mypage/basket/remove")
	public String basketinfo(@RequestParam("sbNo") int sbNo) {
		logger.info("장바구니번호 : {}", sbNo);
		int res = mypageService.deleteBasket(sbNo);
		if(res>0) {
			return "redirect:/mypage/basket";
		}
		return null;
	}
	
	//장바구니 수량 추가
		@RequestMapping(value = "/mypage/basket/plus")
		public String basketPlus(@RequestParam("sbNo") int sbNo) {
			logger.info("장바구니번호 : {}", sbNo);
			int res = mypageService.plusItemBasket(sbNo);
			if(res>0) {
				return "redirect:/mypage/basket";
			}
			return null;
		}
		
		//장바구니 수량 감소
		@RequestMapping(value = "/mypage/basket/minus")
		public String basketMinus(@RequestParam("sbNo") int sbNo) {
			logger.info("장바구니번호 : {}", sbNo);
			int res = mypageService.minusItemBasket(sbNo);
			if(res>0) {
				return "redirect:/mypage/basket";
			}
			return null;
		}
	
	
	//병원관계자 병원관리페이지
	@RequestMapping(value="/mypage/hospital")
	public void hospitalinfo(@RequestParam(defaultValue = "0") int curPage, Model model) {
		
			MypageBoardPaging paging = mypageService.getHospitalPaging(curPage);
			//병원 목록 조회
			List<Hospital> list = mypageService.hospitalList(paging);
			for( int i=0; i<list.size(); i++) {
			logger.debug(list.get(i).toString());
			}
			//모델값 전달
			model.addAttribute("hoslist",list);
			model.addAttribute("paging", paging);
	}
	
	//병원관계자가 병원삭제
	@RequestMapping(value = "/mypage/hospitalDelete")
	public String hospitalDelete(int[] hNoArr) {


		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("hNoArr", hNoArr);
		
		
		int res = mypageService.deleteHospital(map);
		if(res>0) {
			return "redirect:/mypage/hospital";
		}
		return null;
	}
	
	//병원예약 관리 페이지
	@RequestMapping(value="/mypage/hosBooking")
	public void hosbookinginfo(@RequestParam(defaultValue = "0") int curPage, Model model) {
		MypageBoardPaging paging = mypageService.getHosBookingaging(curPage);
		
		//예약 목록 조회
		List<Booking> list = mypageService.bookingList(paging);
		for( int i=0; i<list.size(); i++) {
		logger.debug(list.get(i).toString());
		}
		//모델값 전달
		model.addAttribute("booklist",list);
		model.addAttribute("paging", paging);
	}
	
	//병원예약 상세 정보
	@RequestMapping(value="/mypage/hosBookingDetail", method=RequestMethod.GET)
	public void hosBookingInfoDetail(int bookno, Model model) {
		Booking view = mypageService.view(bookno);
		//model에 첨부파일 속성값 설정
		model.addAttribute("view", view);
	}
	
	//예약 승인
	@RequestMapping(value="/mypage/bookingApprove", method=RequestMethod.POST)
	public String bookingApprove(Booking booking) {
		final int bookno = booking.getBookNo();

		mypageService.bookingApprove(booking);
		

		// 메일 전송 객체 생성 
		final MimeMessagePreparator preparator = new MimeMessagePreparator() {
		@Override public void prepare(MimeMessage mimeMessage) throws Exception { 
		final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
						
						
			helper.setFrom("WhosPet <kul32137@gmail.com>"); // 보내는 사람 <> 이메일 주소
			helper.setTo("kul321japan@gmail.com");  // 받는 사람	
			helper.setSubject( "새로운 예약이 접수되었습니다."); // 제목 
			helper.setText("예약번호"+bookno+"번의 예약이 접수되었습니다.", true); //내용
					} 
					
				}; 
				mailSender.send(preparator); //메일을 보낸다.
		return "redirect: /mypage/info";
	}
	
	
	//예약 거절
	@RequestMapping(value="/mypage/bookingReject", method=RequestMethod.POST)
	public String bookingReject(Booking booking) {
		final int bookno = booking.getBookNo();
		mypageService.bookingReject(booking);
		// 메일 전송 객체 생성 
		final MimeMessagePreparator preparator = new MimeMessagePreparator() {
		@Override public void prepare(MimeMessage mimeMessage) throws Exception { 
		final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
								
								
			helper.setFrom("WhosPet <kul32137@gmail.com>"); // 보내는 사람 <> 이메일 주소
			helper.setTo("kul321japan@gmail.com");  // 받는 사람	
			helper.setSubject( "예약이 반려되었습니다."); // 제목 
			helper.setText("예약번호"+bookno+"번의 예약이 반려되었습니다.", true); //내용
						} 
							
				}; 
			mailSender.send(preparator); //메일을 보낸다.
		
		return "redirect: /mypage/info";
	
	}
	
	//결제
	@RequestMapping(value="/pay/complete", method=RequestMethod.POST)
	public String paytest() {
		return "redirect:/";
	}
	
	
		
	
	
	//구매이력 페이지
	@RequestMapping(value = "/mypage/pay", method=RequestMethod.GET)
	public void payinfo( MypageBoardPaging inData , HttpSession session, Model model) {
		logger.info("/mypage/pay [GET]");
		logger.info("indata : {}", inData);

		User user = (User) session.getAttribute("user");
		int uNo = user.getuNo();
		logger.info("uNo : {}", uNo);
		
		//페이징 계산
		MypageBoardPaging paging = mypageService.getPayPaging(inData);
		paging.setuNo(uNo);
		logger.info("paging : {}", paging);
		//페이징 객체 집어넣기
		
//		List<Order> order = (List<Order>) mypageService.selectOrder(paging);
		List<HashMap<String, Object>> order = (List<HashMap<String, Object>>) mypageService.selectOrder(paging);
		logger.info("order : {}", order);
		
		model.addAttribute("order", order);
		model.addAttribute("paging", paging);
		
		
	}
}

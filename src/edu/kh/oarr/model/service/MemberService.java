package edu.kh.oarr.model.service;

import java.util.Scanner;

import edu.kh.oarr.model.vo.Member;

public class MemberService {
	
	private Scanner sc = new Scanner(System.in);
	
	private Member[] memberArr = new Member[5]; //Member 5칸짜리 객체 배열 선언 및 할당
	
	private Member loginMember = null; //현재 로그인한 회원의 정보를 저장할 변수 선언
	
	public MemberService(){// 기본생성자 안에서 인덱스 초기화
	// memberArr 배열 0, 1, 2 인덱스 초기화
		memberArr[0] =new Member("user01","pass01","홍길동", 30, "서울");
		memberArr[1] =new Member("user02","pass02","김둘리", 8, "경기");
		memberArr[3] =new Member("user03","pass03","고길동", 50, "천안");
		
	}
	
	public void displayMenu() {
		// 일은 하는데요.. 결과값은 없어용
		int menuNum = 0; // 지역 변수
		
		do { // 한 번은 무조건 반복
			System.out.println("=======회원 정보 관리 프로그램 ========");
			System.out.println("1. 회원 가입");
			System.out.println("2. 로그인");
			System.out.println("3. 회원 정보 조회");
			System.out.println("4. 회원 정보 수정");
			System.out.println("0. 프로그램 종료");
			
			System.out.print("메뉴 입력 >>> ");
			menuNum = sc.nextInt(); // 필드에 작성된 Scanner sc 사용
			sc.nextLine(); // 입력버퍼에 남은 개행문자 제거
			
			switch(menuNum) {
			case 1 : System.out.println( signUp() );break;
			case 2 : System.out.println( login() ); break;
			case 3 : System.out.println( selectMember() ); break;
			case 4 :
			// 회원 정보 수정 메소드 수행 후
			// 반환되는 결과를 result 변수에 저장
				int result = updateMember(); // -1 or 1 or 0 
				
				if(result == -1) {
					System.out.println("로그인 후 이용 해주시기 바랍니다.");
				} else if (result == 0 ) {
					System.out.println("회원 정보 수정 실패(비밀번호 불일치)");
				} else {
					System.out.println("회원 정보가 수정되었습니다.");
				} 
				
				 break; 
				 
			case 0 : System.out.println("프로그램 종료!");
			default : System.out.println("잘못 입력 하셨습니다. 다시 입력바랍니다!");
			}			
		}while(menuNum != 0); // menuNum이 0이면 반복종료
	}
	
	
		public String signUp() {
			
			
				System.out.println("\n=========회원 가입=========");
				//객체 배열(memberArr)에 가입한 회원 정보를 저장할 예정
				//--> 새로운 회원 정보를 저장할 공간이 있는지 확인하고
				//  빈 공간의 인덱스 번호를 얻어오기 ---> 새로운 메서드 작성
				
				int index = emptyIndex(); // memberArr 배열에 비어있는 인덱스를 반환 받음
				
				System.out.println("현재 회원 수 : " + index);
				
				if(index == -1) {
					return "회원가입이 불가능 합니다.(인원 수 초과)";
				}
				
				System.out.print("아이디 : ");
				String memberId = sc.next();

				System.out.print("비밀번호 : ");
				String memberPw = sc.next();
				
				System.out.print("비밀번호 확인 : ");
				String memberPw2 = sc.next();
				
				System.out.print("이름 : ");
				String memberName = sc.next();
				
				System.out.print("나이 : ");
				int memberAge = sc.nextInt();
				
				System.out.print("지역 : ");
				String region = sc.next();
				
				//비밀번호, 비밀번호 확인 일치 시 회원가입
				if(memberPw.equals(memberPw2)) {
					//Member객체를 생성해서 할당된 주소를 memberARR 비어있는 인덱스에 대입
					memberArr[index] = new Member(memberId,memberPw, memberName,memberAge,region);
							return "회원 가입 성공!";
				}else {
					return "회원 가입 실패(비밀번호 불일치)";
				}
				
				
				
		}
		//memberArr의 비어있는 인덱스 번호를 반환하는 메서드
		//단, 비어있는 인덱스가 없으면 -1반환
		
		public int emptyIndex(){
			//memberArr배열을 0번 인덱스부터 끝까지 접근해서 참조하는 값이 null인 경우의 인덱스 반환
			for(int i = 0; i < memberArr.length; i++) {
				if(memberArr[i] ==null) {
					return i;
				}
				
				return -1;
			}
			//for 문을 수행했지만 return이 되지 않은 경우 해당 위치 코드가 수행 
			// -> for문에서 return 되지 않았다 == 배열에 빈칸이 없다.
			// --> -1반환
		
		
		}
	

// 참고
 	public String login() {
 		System.out.print("아이디 입력 : ");
 		String memberId = sc.next();
	 
 		System.out.print("패스워드 입력 : ");
 		String memberPw = sc.next();
	 
 		//1) memberArr 배열 내 요소를 순서대로 접근하여 null이 아닌지 확인
 		for(int i = 0; i < memberArr.length; i++) {//회원정보가 있을 경우 
 			if(memberArr[i] != null) {
 				//2)회원정보(memberArr[i])의 아이디, 비밀번호와
 				//입력받은 아이디, 비밀번호가 같은지 확인
 				if(memberArr[i].getMemberId().equals(memberId) && 
 						memberArr[i].getMemberPw().equals(memberPw) ) {
 					//3)로그인 회원 정보 객체(Member)를 참조하는 변수 loginMember에 
 					// 현재 접근중인 memberArr[i] 요소에 저장된 주소를 얕은 복사
				 
 					loginMember = memberArr[i]; // 얕은 복사 == 대입 
 					//(하나의 값이 변경되면 같이 변경)
				 
 					break; // 더 이상 같은 아이디, 비밀번호 없으므로 for문 종료
						 
 				}			
 			}
 		}//for문 끝
	 
 		//4)로그인 성공/실패 여부에 따라 결과값을 반환
 		if(loginMember == null) {//로그인 실패
 			return "아이디 또는 비밀번호가 일치하지 않습니다.";
 		} else {//로그인 성공
 			return  loginMember.getMemberName() + "님 환영합니다.";
 		}
 	}
 	
 	public String selectMember() {
 		System.out.println("\n*****************회원정보 조회*******************");
 	
 		//1)로그인 여부 확인 --> 필드 loginMember가 참조하는 객체가 있는지 확인
 		if(loginMember == null) {
 			return "로그인 후 이용해주시기 바랍니다.";
 		} 
  		//2)로그인이 되어있는 경우
 		String str = "이름 : " + loginMember.getMemberName();
 		
 		// 이름 : 홍길동 
 		// 아이디 : user01 
 		// 나이 : 20 
 		// 지역 : 서울 
 		str += "\n아이디 : " + loginMember.getMemberId();
 		str += "\n나이 :  " + loginMember.getMemberAge + "세"();
 		str += "\n지역 : " + loginMember.getRegion();
 				
 		// 회원 정보를 출력할 문자열 만들어서 변환(return)
 		// (단, 비밀번호 제외)
 		
 	}

 	public int updateMember() {
 		System.out.println("\n****************회원정보수정****************");
 		
 		if(loginMember == null) {
 			return 0-1;
 		}
 		
 		System.out.print("수정할 이름 입력 : ");
 		String inputName = sc.next();
 		

 		System.out.print("수정할 나이 입력 : ");
 		int inputAge = sc.nextInt();
 		sc.nextLine();
 		
 		

 		System.out.print("수정할 지역 입력 : ");
 		String inputRegion = sc.next();
 		
 		
 		
 		//1) 로그인 여부 판별
 		// 로그인이 되어있지 않으면 -1 반환
 		//2) 수정할 회원 정보 입력받기(이름, 나이, 지역)
 		//3) 비밀번호를 입력받아서 로그인한 회원의 비밀번호와 일치하는지 확인
 		System.out.print("비밀번호 입력 : ");
 		String inputPw = sc.next();	
 		
 		if(inputPw.equals(loginMember.getMemberPw())) {
 			loginMember.setMemberAge(inputAge);
 			loginMember.setRegion(inputRegion);
 			
 			return 1;

 		} else {
 			return 0;
 		
 		//4) 비밀번호가 일피하는 경우
 		// 로그인한 회원의 이름,나이, 지역 정보를입력받은 값으로 변경 후 1 반환
 		//5) 비밀번호가 다를 경우 0반환		
 	}
 	
 }
 	public void searchRegion() {
 		System.out.println("***************회원 검색(지역)****************");
 	
 		System.out.print("검색할 지역을 입력하세요 : ");
 		String inputRegion = sc.next();
 		
 		boolean flag = false; // 검색 결과 신호용 변수
 		
 		
 		// 1) memberArr 배열의 모든 요소 순차 접근
 		for(int i = 0; i < memberArr.length; i++) {
 			
 		// 2) memberArr[i] 요소가 null 경우 반복 종료
 			if(memberArr[i] == null) break;
 			
 		// 3) memberArr[i] 요소가 저장된 지역(getRegion)이
 	 	// 입력받은 지역과(inputRegion)과 같을 경우, 회원 아이디/이름 출력	
 			if(memberArr[i].getRegion().equals(inputRegion)) {
 				System.out.printf("아이디 : %s, 이름 : %s\n", memberArr[i].getMemberId(), memberArr[i].getMemberName());
 			} 		
 		}
 
 		// 4) 검색 결과가 없을 경우 "일치하는 검색 결과 없음" 출력
 		if(!flag) {
 			System.out.println("일치하는 검색 결과 없음");		
 		}
 		
 	}
 	
 	
 }
 	

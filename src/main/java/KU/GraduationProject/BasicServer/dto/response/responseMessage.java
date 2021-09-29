package KU.GraduationProject.BasicServer.dto.response;

public class responseMessage {

    //사용자 api
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
    public static final String CREATED_USER = "회원 가입 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";
    public static final String DUPLICATED_USER = "이미 존재하는 계정입니다.";
    public static final String ACCESSTOKEN_SUCCESS = "토큰 발급 성공";
    public static final String INVALID_TOKEN = "유효하지 않은 토큰입니다";

    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";
    public static final String BAD_REQUEST = "적절하지 않은 요청입니다.";

    //파일 api
    public static final String UPLOAD_SUCCESS = "업로드 성공";
    public static final String DOWNLOAD_SUCCESS = "다운로드 성공";
    public static final String UPLOAD_FAIL = "업로드 실패";
    public static final String IMAGE_NOT_FOUND = "이미지를 찾을 수 없습니다.";
    public static final String DELETE_IMAGE = "이미지 삭제 성공";
    public static final String DUPLICATED_IMAGE = "이미 존재하는 이미지입니다.";
}
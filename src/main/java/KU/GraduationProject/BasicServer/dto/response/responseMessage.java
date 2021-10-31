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
    public static final String ACCESS_TOKEN_SUCCESS = "토큰 발급 성공";
    public static final String INVALID_TOKEN = "유효하지 않은 토큰입니다";
    public static final String WRONG_PASSWORD = "잘못된 비밀번호 입니다.";
    public static final String CORRECT_PASSWORD = "비밀번호 인증에 성공했습니다.";

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
    public static final String FORBIDDEN_IMAGE = "사용자가 접근할 수 없는 이미지입니다.";
    public static final String SHOW_IMAGE_LIST = "이미지 목록 조회 성공";


    //project api
    public static final String CREATED_PROJECT = "프로젝트 생성 성공";
    public static final String SAVE_PROJECT = "프로젝트 저장 성공";
    public static final String SHOW_PROJECT_LIST = "프로젝트 목록 조회 성공";
    public static final String FORBIDDEN_PROJECT = "접근 권한이 없는 프로젝트입니다.";
    public static final String DELETE_PROJECT = "프로젝트 삭제 성공";
    public static final String SAVE_MODEL = "3D 모델 업데이트 성공";
    public static final String OPEN_PROJECT = "프로젝트 열람 성공";
    public static final String CREATE_3DMODEL_INPROGRESS = "3D 모델링 중..";
    public static final String CREATE_3DMODEL_SUCCESS = "3D 모델 생성 성공";

}

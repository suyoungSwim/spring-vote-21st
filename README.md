# spring-vote-21st
# 7주차 미션 제출 문서

## 프백 합동과제 협업 환경 세팅

### Git Convention
- Commit message 규칙:
- PR 및 Issue: 템플릿 사용
  - assigner, reviewer, label 반드시 체크하도록 합의
- Branch 전략: Git Flow 전략 사용
- 브랜치 종류
    - main: 배포용 브랜치
    - develop: 개발용 브랜치
    - feature: 기능 단위 개발
    - release: 배포 전 테스트
    - hotfix: 버그 수정
  
    - github branch rules를 적용하여 main, develop 브랜치는 push 금지, only PR merge
    - PR에 대한 리뷰 필수!
    - reviewer가 merge된 브랜치 삭제하기로 함


###  개발 스타일
- 폴더 구조: feature based package 사용(확장성 고려)
- 네이밍 컨벤션: 클래스, 함수, 변수별로 구체적으로 정하진 않음. 공통적으로 줄임말을 쓰지 않고 full naming으로 합의
    - 예외 예약어: DTO
- 예외/응답 처리 방식: 글로벌 인터셉터를 두는 것을 고려중! 
- 주석 형식:

---


## 배포 및 CICD

### 수동 배포 (필수)
- 배포 방식 (예: EC2 + Docker, Vercel 등):
- 배포 주소(URL):

###  CICD
- 진행중!


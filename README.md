# KkamppagE-book
## project_KkamppagE-book_Android
👀 깜빡E-book :: 눈 깜빡임 페이지 조작 안드로이드 앱 구현 👀
* 2020 프로젝트 종합 설계 '깜빡E-book'
* 프로젝트 기간 : 2020.05 ~ 2020.07 (3개월)  
  
  
  > #### * 기획 배경은 깜빡E-book openCV 프로젝트와 동일합니다.  
  > [👉openCV project👈](https://github.com/chaeyun0122/KkamppagE-book_openCV/#기획-배경)  
  
  
## 시스템 동작
![image](https://user-images.githubusercontent.com/79209568/111717983-83a77e80-889c-11eb-8bb8-6e79dfd7783f.png)
  
## Usage
* **Download this apk! [👉깜빡E-book APK👈](https://github.com/Clary0122/KkamppagE-book_Android/blob/4c4c4eb2bb47d1f7fda6289bcb88b9f243d06358/app/release/app-release.apk)**
  
## Demo
![kpEbookAndroid](https://user-images.githubusercontent.com/79209568/111672540-68694e80-885d-11eb-9ff6-656600d17232.gif)

### Key function
* **Book list page** : 책 선택
* **Double blink** : 다음 페이지로 이동
* **Long blink** : 이전 페이지로 이동
* **Camera  on/off switch** : 카메라 화면 스위치 (off해도 동작 가능)
  
## 시스템 설계
![image](https://user-images.githubusercontent.com/79209568/111714343-c82f1c00-8894-11eb-90af-3c30c9d1dfc7.png)
1. ML Kit의 face detection API를 이용해서 카메라로 입력 받는 사용자의 얼굴을 인식인식한다.
2. Open Probability 함수를 사용한다. (눈이 떠져있을 확률을 반환해주는 함수)
3. 실시간으로 사용자의 눈 깜빡임에 따라 0~1 사이의 예측값이 깜빡임 상태 변수(leftstate, rightstate)에 업데이트된다.  
  
  
## 사용된 모듈
* [Firebase ML machine learning KIT](https://firebase.google.com/docs/ml-kit/android/detect-faces)
* [Android PDF Viewer](https://github.com/barteksc/AndroidPdfViewer)


## 깜빡E-book의 다른 프로젝트
* [Python openCV](https://github.com/chaeyun0122/KkamppagE-book_openCV)

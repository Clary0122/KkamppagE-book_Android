# KkamppagE-book
## project_KkamppagE-book_Android
๐ ๊น๋นกE-book :: ๋ ๊น๋นก์ ํ์ด์ง ์กฐ์ ์๋๋ก์ด๋ ์ฑ ๊ตฌํ ๐
* 2020 ํ๋ก์ ํธ ์ขํฉ ์ค๊ณ '๊น๋นกE-book'
* ํ๋ก์ ํธ ๊ธฐ๊ฐ : 2020.05 ~ 2020.07 (3๊ฐ์)  
  
  
  > #### * ๊ธฐํ ๋ฐฐ๊ฒฝ์ ๊น๋นกE-book openCV ํ๋ก์ ํธ์ ๋์ผํฉ๋๋ค.  
  > [๐openCV project๐](https://github.com/chaeyun0122/KkamppagE-book_openCV/#๊ธฐํ-๋ฐฐ๊ฒฝ)  
  
  
## ์์คํ ๋์
![image](https://user-images.githubusercontent.com/79209568/111717983-83a77e80-889c-11eb-8bb8-6e79dfd7783f.png)
  
## Usage
* ### Download this apk! 
  **[๐๊น๋นกE-book APK๐](https://github.com/Clary0122/KkamppagE-book_Android/blob/4c4c4eb2bb47d1f7fda6289bcb88b9f243d06358/app/release/app-release.apk)**
  
## Demo
![kpEbookAndroid](https://user-images.githubusercontent.com/79209568/111672540-68694e80-885d-11eb-9ff6-656600d17232.gif)

### Key function
* **Book list page** : ์ฑ ์ ํ
* **Double blink** : ๋ค์ ํ์ด์ง๋ก ์ด๋
* **Long blink** : ์ด์  ํ์ด์ง๋ก ์ด๋
* **Camera  on/off switch** : ์นด๋ฉ๋ผ ํ๋ฉด ์ค์์น (offํด๋ ๋์ ๊ฐ๋ฅ)
  
## ์์คํ ์ค๊ณ
![image](https://user-images.githubusercontent.com/79209568/111714343-c82f1c00-8894-11eb-90af-3c30c9d1dfc7.png)
1. ML Kit์ face detection API๋ฅผ ์ด์ฉํด์ ์นด๋ฉ๋ผ๋ก ์๋ ฅ ๋ฐ๋ ์ฌ์ฉ์์ ์ผ๊ตด์ ์ธ์ํ๋ค.  
(It uses ML Kit's face detection API to recognize a user's face by received a camera.)
2. Open Probability ํจ์๋ฅผ ์ฌ์ฉํ๋ค. (๋์ด ๋ ์ ธ์์ ํ๋ฅ ์ ๋ฐํํด์ฃผ๋ ํจ์)  
(Use the 'Open Probability' function. (Function that returns the probability of eyes state of being open))
3. ์ค์๊ฐ์ผ๋ก ์ฌ์ฉ์์ ๋ ๊น๋นก์์ ๋ฐ๋ผ 0~1 ์ฌ์ด์ ์์ธก๊ฐ์ด ๊น๋นก์ ์ํ ๋ณ์(leftstate, rightstate)์ ์๋ฐ์ดํธ๋๋ค.  
(Predictions between 0 and 1 are updated in the blink state variable as users blink in real time.)
  
  
## ์ฌ์ฉ๋ ๋ชจ๋
* [Firebase ML machine learning KIT](https://firebase.google.com/docs/ml-kit/android/detect-faces)
* [Android PDF Viewer](https://github.com/barteksc/AndroidPdfViewer)


## ๊น๋นกE-book์ ๋ค๋ฅธ ํ๋ก์ ํธ
* [Python openCV](https://github.com/chaeyun0122/KkamppagE-book_openCV)

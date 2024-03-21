import 'package:bada/login/login_platform.dart';
import 'package:bada/provider/profile_provider.dart';
import 'package:bada/screens/login/initial_screen.dart';
import 'package:bada/screens/main/main_screen.dart';
import 'package:bada/widgets/screensize.dart';
import 'package:flutter/material.dart';
import 'package:kakao_flutter_sdk/kakao_flutter_sdk.dart';
import 'package:provider/provider.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  @override
  Widget build(BuildContext context) {
    final profileProvider = Provider.of<ProfileProvider>(context);
    return Scaffold(
      backgroundColor: Colors.white,
      body: Container(
        padding: const EdgeInsets.all(20),
        child: Center(
          child: Column(
            children: [
              SizedBox(height: UIhelper.scaleHeight(context) * 100),
              const Text(
                '로그인',
                style: TextStyle(
                  fontSize: 40,
                ),
              ),
              SizedBox(
                height: UIhelper.scaleHeight(context) * 100,
              ),
              Image.asset(
                'assets/img/bag.png',
                width: UIhelper.scaleWidth(context) * 200,
                height: UIhelper.scaleHeight(context) * 200,
              ),
              SizedBox(
                height: UIhelper.scaleHeight(context) * 120,
              ),

              // 아이디가 데이터베이스에 없는 경우
              GestureDetector(
                onTap: () async {
                  print('카카오 로그인할 때 작성 해야하는 kakao sdk 키 해쉬');
                  print(await KakaoSdk.origin);

                  LoginPlatform loginPlatform = LoginPlatform.kakao;
                  await profileProvider.initProfile(loginPlatform);

                  if (profileProvider.isLogined) {
                    // if(id 비교해서 데이터베이스에 없으면)
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => const InitialScreen(),
                      ),
                    );
                  }
                },
                child: Image.asset(
                  'assets/img/kakao_login.png',
                  width: UIhelper.scaleWidth(context) * 200,
                  height: UIhelper.scaleHeight(context) * 50,
                ),
              ),
              SizedBox(
                height: UIhelper.scaleHeight(context) * 5,
              ),
              GestureDetector(
                onTap: () async {
                  LoginPlatform loginPlatform = LoginPlatform.naver;
                  await profileProvider.initProfile(loginPlatform);

                  if (profileProvider.isLogined) {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => const InitialScreen(),
                      ),
                    );
                  }
                },
                child: Image.asset(
                  'assets/img/naver_login.png',
                  width: UIhelper.scaleWidth(context) * 200,
                  height: UIhelper.scaleHeight(context) * 50,
                ),
              ),

              ElevatedButton(
                onPressed: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => const HomeScreen(),
                    ),
                  );
                },
                child: const Text('메인 가기'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
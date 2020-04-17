package com.lec.android.a008_recycler;

//샘플 데이터
public class D {
    public static int [] FACEID = {
            R.drawable.face01,
            R.drawable.face02,
            R.drawable.face03,
            R.drawable.face04,
            R.drawable.face05,
            R.drawable.face06,
            R.drawable.face07,
            R.drawable.face08,
            R.drawable.face09,
            R.drawable.face10,
            R.drawable.face11,
            R.drawable.face12,
            R.drawable.face13,
            R.drawable.face14,
            R.drawable.face15,
            R.drawable.face16,
            R.drawable.face17,
            R.drawable.face18,
            R.drawable.face19,
            R.drawable.face20,
            R.drawable.face21
    };

    public static final String [] NAME = {
            "아이언맨", "캡틴아메리카", "헐크", "블랙위도우", "팔콘", "울트론",
            "로키", "토르", "그루트", "스타로드", "비젼", "앤트맨", "윈터솔져",
            "로난", "토끼", "스파이더맨", "호크아이", "워머신", "가모라", "베놈",
            "디스트로이어"
    };

    public static final String [] PHONE = {
            "001-1111-1111",
            "002-1111-1111",
            "003-1111-1111",
            "004-1111-1111",
            "005-1111-1111",
            "006-1111-1111",
            "007-1111-1111",
            "008-1111-1111",
            "009-1111-1111",
            "010-1111-1111",
            "011-1111-1111",
            "012-1111-1111",
            "013-1111-1111",
            "014-1111-1111",
            "015-1111-1111",
            "016-1111-1111",
            "017-1111-1111",
            "018-1111-1111",
            "019-1111-1111",
            "020-1111-1111",
            "021-1111-1111"
    };

    public static final String [] EMAIL = {
            "001@mail.com",
            "002@mail.com",
            "003@mail.com",
            "004@mail.com",
            "005@mail.com",
            "006@mail.com",
            "007@mail.com",
            "008@mail.com",
            "009@mail.com",
            "010@mail.com",
            "011@mail.com",
            "012@mail.com",
            "013@mail.com",
            "014@mail.com",
            "015@mail.com",
            "016@mail.com",
            "017@mail.com",
            "018@mail.com",
            "019@mail.com",
            "020@mail.com",
            "021@mail.com"
    };
    private static int idx = 0;

    public static int next(){
        idx = idx % FACEID.length;
        return idx++; // idx 값 리턴하고 1 증가
    }


}

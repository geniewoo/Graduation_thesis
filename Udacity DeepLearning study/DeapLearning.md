# 실습 개념 정리

### 1. np

##### import numpy as np

##### - a. np.ndarray(shape = (2, 2), dtype = float);

    parameter1 = 생긴 모습 integer인 경우 1차 array, shape = (2, 2) 면 2 x 2 array, shape = (2, 2, 2), 2 x 2 x 2 array
    이후 파라미터, dtype = 데이터 타입 지정

##### - 그 외

    -평균
    np.mean(A)
    -중간값
    np.median(A)
    -분산
    np.var(A)
    -표준편차
    np.std(A)
    -합
    np.sum(A)
    -누적합
    np.cumsum(A)

    -Array 합계
    a.sum(axis=0) -> 열 합계
    a.sum(axis=1) -> 행 합계

    -Transpose
    np.tarnspose(A)

    -Inverse metrix
    np.linalg.inv(A)

    -행렬 내적
    np.dot(A,B)

    -행렬 스칼라 곱
    A * 2 -> 각 원소에 scalar 곱이 됨.

    -단위행렬 생성
    np.identuty(a)

    -numpy 연산
    np.exp(a) -> 행렬 모든 원소에 exponential 취함
    np.floor(a) -> 각 원소의 값보다 작거나 같은 정수 중 가장 작은 수를 반환한다.

### 2. os

##### import os

##### -a. os.listdir(folderName)

    폴더 안에 있는 폴더, 파일이름을 가져옴

##### -b os. path.join(앞이름, 뒤이름)

    os마다 다른 폴더 구분자를 이용하여 파일 주소를 이어줌

### 3. pickle

##### from six.moves import cPickle as pickle

##### -a pickle.dump(obj, file, protocol, \*(옵션), fix_imports=True(옵션))

    리스트를 바이너리형태로 파일에 다 씀
    obj = 넣을 리스트, file = 파일이름
    protocol = 프로토콜 버전 HIGHEST_PROTOCOL를 사용하자

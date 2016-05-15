# 로그프레소 클라이언트 SDK

## 자바 API
로그프레소 클라이언트 SDK는 자바 6 이상의 환경에서 구동되며, 쿼리 실행, 수집기, 트랜스포머, 파서, 테이블, 인덱스, 예약된 쿼리, 스트림 쿼리 관리 등 로그프레소를 원격에서 제어하는데 필요한 모든 API를 제공합니다.

### 자바 API 시작하기

```
 Logpresso client = null;
 Cursor cursor = null;
 
 try {
        client = new Logpresso();
        client.connect("localhost", 8888, "root", "PASSWORD");
        cursor = client.query("system tables | fields table");
 
        while (cursor.hasNext()) {
                System.out.println(cursor.next());
        }
 } finally {
        if (cursor != null)
                cursor.close();
 
        if (client != null)
                client.close();
 }
 
```

## 스크립트 실행
로그프레소 클라이언트 SDK 바이너리를 실행하면, 쉘이나 명령줄에서 즉시 로그프레소 쿼리를 실행할 수 있습니다.

-e “쿼리문자열” 스위치를 이용하면 인터랙티브 콘솔로 들어가지 않고 쉘에서 즉시 쿼리를 실행할 수 있습니다. 표준 출력을 리다이렉트하여 파일에 저장하면 타 시스템과 쉽게 연동할 수 있습니다. 아래 스위치를 지정할 수 있습니다:

* [필수] -h: 로그프레소 서버가 설치된 도메인 혹은 IP 주소를 입력합니다.
* [필수] -P: 로그프레소 서버의 웹 포트 번호를 입력합니다.
* [필수] -u: 계정 이름을 입력합니다.
* [필수] -p: 암호를 입력합니다. 빈 암호인 경우 -p ""로 입력합니다.
* [선택] -c: CSV 파일 포맷으로 출력하며, 출력할 필드 이름들을 쉼표로 구분하여 순서대로 나열합니다.
* [선택] -f: -e 스위치 뒤에 쿼리 문자열을 입력하지 않고 쿼리가 저장된 파일을 참조할 때 사용합니다. 가령 -f query.txt 를 지정하면 query.txt 파일에 저장된 쿼리 문자열을 사용합니다. 이 때 파일 내용 중 #으로 시작하는 줄은 주석으로 무시됩니다.

표준 출력의 인코딩을 변경하려면 -Dfile.encoding 옵션을 java 실행 옵션으로 지정합니다. (-jar 앞 부분)

#### 쿼리 스크립트 예시
**실행 예시**

```
$ java -jar logpresso-sdk-java-1.0.0-package.jar -e "system tables | fields table" -h "localhost" -P "8888" -u "root" -p "PASSWORD"
```

**출력 결과**
```
{table=sys_table_trends}
{table=araqne_query_logs}
{table=sys_alerts}
{table=sys_audit_logs}
{table=sys_query_logs}
{table=sys_cpu_logs}
{table=sys_mem_logs}
{table=sys_gc_logs}
{table=sys_disk_logs}
{table=sys_node_logs}
{table=sys_logger_trends}
```

#### CSV 포맷 출력 예시
**실행 예시**
```
$ java -jar logpresso-sdk-java-1.0.0-package.jar -e "system logdisk | stats sum(disk_usage) as usage by table | sort limit=5 -usage" -h "localhost" -P "8888" -u "root" -p "PASSWORD" -c "table, usage"
```

**출력 결과**
```
"sys_mem_logs","617508"
"sys_cpu_logs","301374"
"sys_table_trends","207410"
"sys_disk_logs","35556"
"araqne_query_logs","28402"
```

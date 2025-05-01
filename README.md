Build Scheduler Quartz với k8s nhìu pod chạy cùng lúc check job <br> 

B1: Điền địa chỉ mysql vào file /k8s/deployment <br>
B2: run file quartz.script vào database quartzDemo <br>
B3. run file k8s <br>
  - kubectl apply -f backend-deployment.yaml
  - kubectl apply -f backend-nodeport.yaml
<br>
B4. test API  <br>
- http://x.x.x.x:32081/api/v1/customers # API GET ALL CUSTOMERS
<br>
- http://x.x.x.x:32081/health #api health check

<br>
API Tạo schedule cron mỗi phút 1 lần
<br>
curl --location 'http://x.x.x.x:32081/schedule/create' \
--header 'Content-Type: application/json' \
--data '{
    "jobName": "CustomerJobEveryMiniuteAdd10Point",
    "jobGroup": "Marketing",
    "jobDescription": "Cong diem cho khach hang moi 1s cong 10d",
    "jobClass": "CustomerJob",
    "runForever": true,
    "cronExpression": "0 0/1 * * * ?" 

}'

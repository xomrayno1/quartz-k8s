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

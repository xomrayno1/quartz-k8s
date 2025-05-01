B1: Điền địa chỉ mysql vào file /k8s/deployment
B2: run file quartz.script vào database quartzDemo
B3. run file k8s
  - kubectl apply -f backend-deployment.yaml
  - kubectl apply -f backend-nodeport.yaml
B4. test API
- http://x.x.x.x:32081/api/v1/customers # API GET ALL CUSTOMERS
- http://x.x.x.x:32081/health #api health check

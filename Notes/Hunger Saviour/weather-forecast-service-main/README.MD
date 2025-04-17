## Kubernetes Commands
First run 'mvn clean install' followed by 'docker compose up'
1) kubectl apply -f deployment.yaml (create deployment)
2) kubectl get deployments
3) kubectl get pods
4) kubectl logs image-name (To get logs of pod)
5) kubectl apply -f service.yaml (create service)
6) kubectl get service (service details)
7) kubectl get nodes -o wide (This will give you IP address) 
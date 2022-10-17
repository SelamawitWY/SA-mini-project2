## Name of members

- Esmelealem Mihretu: 614695
- Selamawit Wodeyohannes: 614647
- Zedagem Demelash: 614742

## How to run our project

- Make sure you have docker setup on your machine
- Make sure you have kubernetes setup on your machine
- Make sure you install helm and kafka

  > > brew install helm
  > > helm repo add bitnami https://charts.bitnami.com/bitnami
  > > helm install my-release bitnami/kafka

- Deploy services by running the following command in the release folder

  > > kubectl apply -f .

- Create account and login

  > > POST http://localhost:8189/accounts/create create an account

  > > POST http://localhost:8189/authenticate using email and password of the created account

  Add the generated token to test the rest APIs

- Demo Video Link:
  https://drive.google.com/file/d/1gxqm_2-2jAUlqc04iejbNOVXNKk2bIAu/view?usp=sharing

## NOTE: Postman request collections is attached

https://www.getpostman.com/collections/36a1e6772a09811dc6d3

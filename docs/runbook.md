# Local runbook

1. `docker compose up -d mysql redis kafka kafka-ui`
2. `cd backend && mvn test`
3. `cd backend && mvn spring-boot:run`
4. `cd frontend && npm install && npm run dev`
5. Open http://localhost:5173

Kafka UI: http://localhost:8088

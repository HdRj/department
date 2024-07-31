# Getting Started

### Run in docker
1. **Create .evn file in root of project**

MAIL_USERNAME=

MAIL_PASSWORD=

JWT_SECRET_KEY=

SERVICE_ADDRESS=

2. **Build project**

```bash
mvn package -Dmaven.test.skip
```

3. **Create a docker-image**

```bash
docker build -t department:latest .
```

4. **Run**

```bash
docker-compose up -d
```

or

```bash
docker-compose up
```

5. **Stop**

```bash
docker-compose down
```


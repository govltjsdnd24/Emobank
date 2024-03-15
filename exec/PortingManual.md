- Front Docker 실행

```bash
docker build -t emobank-front . 
docker run -d -p 80:3000 --name fe-emobank emobank-front
```
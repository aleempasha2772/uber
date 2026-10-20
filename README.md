# Uber Microservices Project

A learning project that models a small ride-booking platform using Spring Boot microservices. The repository currently contains Rider, Captain, and Trip services, plus Apache Kafka for captain-location events.

## Services

| Service | Port | Responsibility |
|---|---:|---|
| Rider Service | `8080` | Creates, reads, lists, deletes, and looks up riders by phone number |
| Captain Service | `8081` | Manages captains and publishes simulated captain-location updates to Kafka |
| Trip Service | `8082` | Creates fare quotes and creates/manages trips from quote IDs |
| Apache Kafka | `9092` | Carries captain-location messages on the `captain-location` topic |

All services use Java 17, Spring Boot, Spring Data JPA, and in-memory H2 databases. The Trip service uses OpenFeign to retrieve rider information from the Rider service.

## Repository structure

```text
uber/
├── rider/          Rider microservice
├── captain/        Captain microservice and Kafka producer
├── trip-service/   Quote and trip microservice
└── docker-compose.yml
```

## API endpoints

### Rider Service

Base URL: `http://localhost:8080/api/v1/riders`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/create-rider` | Create a rider |
| `GET` | `/get-rider/{riderId}` | Get a rider by ID |
| `GET` | `/getAll-riders` | Get all riders |
| `GET` | `/by-phone?phoneNumber={phone}` | Find a rider by phone number |
| `DELETE` | `/delete-rider/{riderId}` | Delete a rider |

Example rider request:

```json
{
  "username": "rider1",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "phone": "9876543210",
  "password": "password",
  "role": "RIDER",
  "status": "ACTIVE"
}
```

### Captain Service

Base URL: `http://localhost:8081/api/v1/captains`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/create-captain` | Create a captain |
| `GET` | `/get-captain/{captainId}` | Get a captain by ID |
| `GET` | `/getAll-captains` | Get all captains |
| `DELETE` | `/delete-captain/{captainId}` | Delete a captain |
| `PUT` | `/` | Publish 10 simulated location updates to Kafka |

Example captain request:

```json
{
  "username": "captain1",
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jane@example.com",
  "phone": "9876543211",
  "password": "password",
  "role": "CAPTAIN",
  "status": "ACTIVE",
  "licenseNumber": "DL-12345",
  "vehicleType": "SEDAN",
  "vehiclePlate": "TS09AB1234"
}
```

Publish the simulated captain locations:

```bash
curl -X PUT http://localhost:8081/api/v1/captains
```

### Trip Service

Base URL: `http://localhost:8082/api/v1/trips`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/quote` | Calculate and persist a trip quote |
| `POST` | `/?quoteId={quoteId}` | Create a trip from a valid quote |
| `GET` | `/{tripId}` | Get a trip by ID |
| `GET` | `/` | Get all trips |
| `GET` | `/rider/{riderId}` | Get trips belonging to a rider |
| `PATCH` | `/{tripId}/status?status={status}` | Update a trip status |
| `DELETE` | `/{tripId}` | Delete a trip |
| `GET` | `/rider/by-phone?phoneNumber={phone}` | Retrieve rider data through the Rider service |

Request a quote:

```bash
curl -X POST http://localhost:8082/api/v1/trips/quote \
  -H "Content-Type: application/json" \
  -d '{
    "phoneNumber": "9876543210",
    "pickupLocation": {
      "latitude": 17.385,
      "longitude": 78.4867
    },
    "dropLocation": {
      "latitude": 17.4401,
      "longitude": 78.3489
    }
  }'
```

The response contains a `quoteId`. Use it before the quote expires:

```bash
curl -X POST \
  "http://localhost:8082/api/v1/trips?quoteId=YOUR_QUOTE_ID"
```

A quote can create only one trip. Used quotes return `409 Conflict`, expired quotes return `410 Gone`, and unknown quote IDs return `404 Not Found`.

## Running the project

### Requirements

- Java 17
- Docker Desktop or Docker Engine with Compose
- A Eureka server on `localhost:8761` when testing Trip-to-Rider communication

The services include Eureka client dependencies, but this repository does not currently contain a Eureka server. The Trip service's Rider Feign client uses the service name `rider`, so service discovery must be available for that integration.

### 1. Start Kafka

From the repository root:

```bash
docker compose up -d
```

Check its status or logs:

```bash
docker compose ps
docker compose logs -f kafka
```

### 2. Start the services

Run each service in a separate terminal:

```bash
cd rider
./mvnw spring-boot:run
```

```bash
cd captain
./mvnw spring-boot:run
```

```bash
cd trip-service
./mvnw spring-boot:run
```

The Captain service creates the `captain-location` topic through its Kafka configuration when it starts.

### 3. Stop Kafka

```bash
docker compose down
```

## H2 consoles

The databases are in memory, so their contents are lost whenever a service restarts.

### Rider database

- Console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `aleem`
- Password: `aleem`

### Captain database

- Console: `http://localhost:8081/h2-console`
- JDBC URL: `jdbc:h2:mem:captaindb`
- Username: `sa`
- Password: empty

## Current implementation status

Implemented:

- Rider and captain CRUD operations
- Rider lookup by phone number
- Fare quote generation with expiry
- One-time quote consumption when creating a trip
- Trip retrieval, listing, status updates, and deletion
- Kafka topic creation and captain-location publishing

Planned architecture items such as nearby-captain matching, ride-offer consumption, captain acceptance events, rider notifications, and trip OTP verification are not yet implemented.

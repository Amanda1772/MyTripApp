# Travel App Data Model (Written Schema)

## Users

| Field Name | Type | Notes |
|---|---|---|
| user_id (PK) | INT | unique user |
| name | VARCHAR | full name |
| email | VARCHAR | unique |
| password_hash | VARCHAR | stored securely |
| created_at | DATETIME | created timestamp |

## Trips

| Field Name | Type | Notes |
|---|---|---|
| trip_id (PK) | INT | unique trip |
| user_id (FK) | INT | references Users.user_id |
| title | VARCHAR | trip name |
| destination | VARCHAR | main destination |
| start_date | DATE | start |
| end_date | DATE | end |
| created_at | DATETIME | created timestamp |

## TripItems

| Field Name | Type | Notes |
|---|---|---|
| item_id (PK) | INT | unique item |
| trip_id (FK) | INT | references Trips.trip_id |
| type | VARCHAR | hotel, flight, activity |
| name | VARCHAR | item name |
| location | VARCHAR | location |
| start_time | DATETIME | start time |
| end_time | DATETIME | end time |
| notes | TEXT | extra details |

## Favorites

| Field Name | Type | Notes |
|---|---|---|
| favorite_id (PK) | INT | unique favorite |
| user_id (FK) | INT | references Users.user_id |
| place_id | VARCHAR | external maps id |
| label | VARCHAR | user label |
| created_at | DATETIME | saved timestamp |

## Relationships
- Users (1) to Trips (many)
- Trips (1) to TripItems (many)
- Users (1) to Favorites (many)

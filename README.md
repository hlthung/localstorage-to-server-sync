# localstorage-to-server-sync

Demonstrates how to sync data between a local db (using SQLite database) and a central PostgreSQL
database via HTTP request. 

- Collects and stores data locally using SQLite.
- Syncs the local data with a remote database using HTTP requests.
- Sends the data to an API service that interacts with the central PostgreSQL database, retrieving or updating data as needed.

### Sync Logic
Uses a scheduled job to trigger data sync at 12pm. The job is set to require any type of network (`NETWORK_TYPE_ANY`), so if there is no network connection available at the scheduled time, the job will not run. The system will wait until any available network (WiFi or mobile data) connection is established before running the job. By doing this, the app shall not show notifications to users saying data syncing failed at the scheduled time if there is no network connection available. The app will also sync data whenever a network connection is established.
 


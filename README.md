# RMI-AI

PoC project for demonstration of Spring AI capabilities in Raw Material Impact application.

## Overview

RMI-AI is a Spring Boot application that demonstrates the integration of AI models (Anthropic Claude, Ollama) for interacting with container and raw material data. The application exposes RESTful endpoints for managing containers and base volumes, and provides an AI-powered chat interface for advanced data interaction.

## Features
- REST API for managing containers and base volumes
- AI-powered chat endpoint for natural language interaction with container data
- Integration with Anthropic Claude and Ollama models (configurable via profiles)
- PostgreSQL database support

## Configuration & Profiles
The application supports multiple profiles for different AI model integrations:
- `application.yml` – Default configuration (database, logging, etc.)
- `application-anthropic.yml` – Anthropic Claude model integration
- `application-ollama.yml` – Ollama model integration

To run with a specific profile, use:

```sh
SPRING_PROFILES_ACTIVE=anthropic ./gradlew bootRun
# or
SPRING_PROFILES_ACTIVE=ollama ./gradlew bootRun
```

## Requirements
- Java 24
- PostgreSQL

## Running the Application

1. Configure your database and environment variables as needed (see `application.yml`).
2. Choose the desired AI model profile and set any required API keys.
3. Start the application:
   ```sh
   ./gradlew bootRun
   ```
   
## License
This project is for demonstration purposes only.


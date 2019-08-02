# Java EE Login Sample

> Note: This sample is in progress, and subject to change.

## Getting started

This sample demonstrates how to use Auth0 to perform authentication using Java EE and the `mvc-auth-commons` SDK. Download or clone this repository and follow the instructions below to setup the sample.

### Auth0 Dashboard

1. On the [Auth0 Dashboard](https://manage.auth0.com/#/clients) create a new Application of type **Regular Web Application**.
1. On the **Settings** tab of your application, add the URL `http://localhost:3000/callback` to the **Allowed Callback URLs** field.
1. On the **Settings** tab of your application, add the URL `http://localhost:3000/` to the **Allowed Logout URLs** field.
1. Copy the `Domain`, `Client ID` and `Client Secret` values at the top of the page and use them to configure the Java Application.

### Application configuration

Copy `src/main/resources/project-defaults.yml.example` to `src/main/resources/project-defaults.yml`:

```bash
cp src/main/resources/project-defaults.yml.example src/main/resources/project-defaults.yml
```

Set the application values in the `project-defaults.yml` file to the values of your Auth0 application. They are read by the `AppConfig` class.

```yaml
auth0:
  domain: {DOMAIN}
  clientId: {CLIENT_ID}
  clientSecret: {CLIENT_SECRET}
```

### Running the sample

Open a terminal, go to the project root directory and execute the `thorntail:run` Maven goal.

Linux/macOS:
```bash
./mvnw clean thorntail:run
```

Windows:
```bash
mvnw.cmd clean thorntail:run
```

The server will be accessible on https://localhost:3000.

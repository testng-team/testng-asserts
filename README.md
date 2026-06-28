# TestNG Asserts

This repository houses the standalone TestNG assertion library: `org.testng.Assert`,
`org.testng.asserts.SoftAssert` and the related assertion classes.

These classes were historically bundled inside the main `org.testng:testng` artifact. They now
live here and are published as the standalone `org.testng:testng-asserts` artifact so they can
evolve and be released independently.

## Pre-requisites

* JDK 11 (or higher)

`org.testng.Assert` delegates the behaviour-identical assertions (`assertNull`/`assertNotNull`/
`assertSame`/`assertNotSame`, `assertThrows`) to [AssertJ](https://assertj.github.io/doc/), so
`assertj-core` is the only runtime dependency (brought transitively). TestNG is required at **test
scope** only (to run the assertion tests via the TestNG runner).

## Usage

```xml
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng-asserts</artifactId>
    <version>1.0.0</version>
    <scope>test</scope>
</dependency>
```

## Migration

If you previously relied on `org.testng.Assert` / `org.testng.asserts.*` coming transitively from
`org.testng:testng`, add `org.testng:testng-asserts` explicitly.

For new code or larger refactoring efforts, [AssertJ](https://assertj.github.io/doc/) is recommended.
See [docs/MIGRATING_ASSERTIONS.md](docs/MIGRATING_ASSERTIONS.md) for the migration guide, including
the automated OpenRewrite recipe.

## Building

```bash
./mvnw clean test
```

## Releasing

Artifacts are published to Maven Central through the
[Central Portal](https://central.sonatype.com/). The `deploy.yml` workflow runs
`./mvnw clean deploy -Pdeploy` on every push to `main`:

* a `-SNAPSHOT` version publishes to the Central Portal snapshot repository;
* a release (non-snapshot) version is staged and auto-published to Maven Central.

The following repository (or organization) secrets must be configured:

* `MAVEN_USERNAME` / `MAVEN_PASSWORD` — a [Central Portal user token](https://central.sonatype.com/account)
  (Account → Generate User Token). These are **not** the legacy OSSRH credentials.
* `GPG_PRIVATE_KEY` — the ASCII-armored GPG private key used to sign artifacts.
* `GPG_PASSPHRASE` — the passphrase for that key.

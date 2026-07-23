# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- `Assert.assertNotEquals(float[], float[], delta)` and
  `Assert.assertNotEquals(double[], double[], delta)` overloads (with and without a message),
  the missing counterparts of the existing `assertEquals(...delta)` array assertions
  ([#11](https://github.com/testng-team/testng-asserts/issues/11)).

### Changed

- `Assert.assertEquals(String, String, String)` now delegates to AssertJ, producing a readable
  character-level diff on mismatch. The failure message format changes accordingly
  ([#18](https://github.com/testng-team/testng-asserts/issues/18)).

### Fixed

- `Assert.assertNotEquals(Collection, Collection)` is now the exact inverse of
  `assertEquals(Collection, Collection)`: it compares by size and element order instead of relying
  on the collections' `equals()`, so a `List` and a `Set` with the same ordered content are no
  longer reported as both equal and not-equal
  ([#14](https://github.com/testng-team/testng-asserts/issues/14)).

## [1.0.0]

First standalone release of `org.testng:testng-asserts`.

### Added

- Standalone publication of the TestNG assertion classes (`org.testng.Assert`,
  `org.testng.asserts.SoftAssert` and related classes), extracted from the main
  `org.testng:testng` artifact so they can evolve and be released independently.
- Maven build with Central Portal publishing (`central-publishing-maven-plugin`),
  GPG signing, and source/javadoc jars.

### Changed

- Replaced the `testng-collections` helpers with JDK equivalents, removing that
  internal dependency.

[Unreleased]: https://github.com/testng-team/testng-asserts/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/testng-team/testng-asserts/releases/tag/v1.0.0

.PHONY: build
build:
	./gradlew buildFatJar

.PHONY: up
up:
	./gradlew runFatJar

.PHONY: unitTest
unitTest:
	./gradlew unitTest

.PHONY: integrationTest
integrationTest:
	./gradlew integrationTest

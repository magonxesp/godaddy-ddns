# Godaddy DDNS
Program for update the hostnames ip on godaddy domains

Requeriments:
* Gradle 8.7
* GraalVM 21

## ⚠️ CAUTION ⚠️

This might don't work because GoDaddy is limited the API for users that meet the following requeriments.

* Availability API: Limited to accounts with 50 or more domains
* Management and DNS APIs: Limited to accounts with 10 or more domains and/or an active Discount Domain Club plan.

## Build
Build the native image executable

```sh
$ ./gradlew nativeCompile
```
And get the executable file in ```build/native/nativeCompile``` directory

Or build a ``jar`` archive

```sh
$ ./gradlew build
```

The executable jar will be ```build/libs/godaddy-ddns-0.0.0-all.jar```

## Usage
### Using the executable file
* Configure the program
```shell script
$ godaddyddns configure
```
* Updating the configured hostnames ip with the current ip
```shell script
$ godaddyddns sync
```

# Godaddy DDNS
Program for update the hostnames ip on godaddy domains

Require python >= 3.7

## Build
Build the executable file using the ```build.sh``` script
```shell script
$ chmod +x build.sh
$ ./build.sh
```
And get the executable file on ```dist``` directory

## Usage
### Using the executable file
* Configure the program
```shell script
$ godaddyddns --configure
```
* Updating the configured hostnames ip with the current ip
```shell script
$ godaddyddns
```

### Using python virtualenv
1. Create the virtualenv and activate
    ```shell script
    $ python3 -m venv venv
    $ venv/bin/activate
    ```
1. Install the dependencies
    ```shell script
    $ pip install -r requirements.txt
    ```
2. Configure the program
    ```shell script
    $ python -m godaddy_ddns --configure
    ```
3. Run for update the hostnames ip with the current ip
    ```shell script
    $ python -m godaddy_ddns
    ```
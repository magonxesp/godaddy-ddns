# Godaddy DDNS
Program for update the hostnames ip on godaddy domains

Require python >= 3.7

## Usage
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
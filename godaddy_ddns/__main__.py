import godaddy_ddns.ddns
import godaddy_ddns.settings
import argparse


def main():
    settings = godaddy_ddns.settings.Settings()
    config = settings.get_settings()

    # Initiate the parser
    parser = argparse.ArgumentParser()
    parser.add_argument("-c", "--configure", help="Configure the program", action="store_true")
    args = parser.parse_args()

    if args.configure:
        settings.configure()
    elif config:
        domain = godaddy_ddns.ddns.Domain(config['domain'], config['api_key'], config['api_secret'])
        domain.update(config['hostnames'])
    else:
        print("The program is not configured, please run with --configure option for configure")


if __name__ == "__main__":
    main()

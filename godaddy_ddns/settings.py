import os
from yaml import dump, load


class Settings:

    def __init__(self):
        self.path = os.path.join(os.path.expanduser('~'), '.godaddy_ddns')
        self.file = 'settings.yml'
        self.settings = {}

    def create_settings(self, settings):
        if os.path.exists(self.path) is not True:
            os.mkdir(self.path)

        file = open(os.path.join(self.path, self.file), "w+")
        dump(settings, file)

    def get_settings(self):
        if bool(self.settings) is False:
            try:
                file = open(os.path.join(self.path, self.file), "r")
                self.settings = load(file)
            except OSError as e:
                return {}

        return self.settings

    def get(self, setting):
        return self.settings[setting]

    def configure(self):
        self.settings['domain'] = input("Domain: ")
        self.settings['api_key'] = input("Godaddy API key: ")
        self.settings['api_secret'] = input("Godady API secret: ")
        hostnames = []

        while True:
            hostnames.append(input("Host name will be updated: "))
            add_other = input("Add another host name? (Yes/No) ")

            if add_other.lower() not in ['yes', 'y']:
                break

        self.settings['hostnames'] = hostnames
        self.create_settings(self.settings)


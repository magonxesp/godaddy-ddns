from urllib.request import urlopen, Request
from urllib.error import HTTPError
from http.client import HTTPResponse
from json import dumps, loads


class Domain:

    def __init__(self, domain, api_key, api_secret):
        self.domain = domain
        self.api_key = api_key
        self.api_secret = api_secret
        self.godaddy_api_key = "{}:{}".format(self.api_key, self.api_secret)

    def get_current_ip(self):
        try:
            response: HTTPResponse = urlopen('https://api.ipify.org')

            if response.status == 200:
                return response.read().decode('utf-8')
        except HTTPError as e:
            print(str(e))

        return None

    def _godaddy_request(self, url, data=None, method='GET'):
        headers = {
            "Content-Type": "application/json",
            "Authorization": "sso-key {}".format(self.godaddy_api_key)
        }

        request = Request(url, data, headers, method=method)

        try:
            response: HTTPResponse = urlopen(request)

            if response.status == 200:
                response_data = response.read().decode('utf-8')
                content_type = response.getheader('Content-Type')

                if content_type == 'application/json':
                    response_data = loads(response_data)

                return response_data
        except HTTPError as e:
            print(str(e))

        return None

    def update_hostname(self, hostname, ip):
        data = [{"data": ip}]
        url = 'https://api.godaddy.com/v1/domains/{}/records/A/{}'.format(self.domain, hostname)
        response = self._godaddy_request(url, dumps(data).encode('utf-8'), 'PUT')

        if response is not None:
            return True
        else:
            return False

    def get_hostname_ip(self, hostname):
        url = 'https://api.godaddy.com/v1/domains/{}/records/A/{}'.format(self.domain, hostname)
        response = self._godaddy_request(url)

        if response is not None:
            hostname_ip = response[0]['data']
            return hostname_ip
        else:
            return ''

    def update(self, hostnames):
        current_ip = self.get_current_ip()

        if current_ip:
            for hostname in hostnames:
                if self.get_hostname_ip(hostname) is not current_ip:
                    self.update_hostname(hostname, current_ip)

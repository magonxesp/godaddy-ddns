from setuptools import setup

setup(
    name='godaddy-ddns',
    version='1.0.0',
    packages=['godaddy_ddns'],
    url='',
    license='MIT',
    author='magonxesp',
    author_email='janma.360@gmail.com',
    description='Update the hostnames ip on godaddy domains.',
    install_requires=[
        "pyyaml"
    ]
)

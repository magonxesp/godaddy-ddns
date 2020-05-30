if [ ! -d venv ]; then
  python3 -m venv venv
  venv/bin/pip3 install -r requirements.txt
fi

EXE_NAME = "godaddyddns"
SITE_PACKAGES_PATH = $(find venv -type d -name site-packages)

pyinstaller \
  --onefile \
  --name $EXE_NAME \
  --paths $SITE_PACKAGES_PATH \
  --exclude-module tkinter \
  cli.py
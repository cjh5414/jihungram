#coding: utf-8
from app import create_app

if __name__ == '__main__':
    app = create_app()
    app.run(host='192.168.123.110', port=8080)
else:
    app = create_app()
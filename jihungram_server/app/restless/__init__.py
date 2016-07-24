#coding: utf-8
from app.database import DBManager
from app.model.user import User
from app.model.article import Article
from flask_restless import APIManager

def initRestlessApi(app):
    manager = APIManager(app, flask_sqlalchemy_db=DBManager.db)
    manager.create_api(User, methods=['GET', 'POST', 'DELETE'])
    manager.create_api(Article, methods=['GET', 'POST', 'DELETE'])


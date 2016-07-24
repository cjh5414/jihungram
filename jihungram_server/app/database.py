# -*- coding: utf-8 -*-
from flask_sqlalchemy import SQLAlchemy

class DBManager:
    db = None

    @staticmethod
    def init(app):
        db = SQLAlchemy(app)
        # migrate = Migrate(app, db)
        DBManager.db = db

    @staticmethod
    def init_db():
        db = DBManager.db
        db.create_all()

        #add init data
        DBManager.init_sample(db)

    @staticmethod
    def clear_db():
        DBManager.db.drop_all()

    @staticmethod
    def init_sample(db):
        from app.model.article import Article
        article = Article('Hello World', 'jihun', 'dfkasdjfl', 'Hello World', '2013-09-23-10:10', 'img1.jpg')

        db.session.commit()



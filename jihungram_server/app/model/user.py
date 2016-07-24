# -*- coding: utf-8 -*-
from app.database import DBManager
db = DBManager.db
class User(db.Model):
    id = db.Column(db.String(120), unique=True, primary_key=True)
    connDate = db.Column(db.String(200))
    token = db.Column(db.String(500))

    def __init__(self, id, connDate, token):
        self.id = id
        self.connDate = connDate
        self.token = token

    def __repr__(self):
        return "<User %r>" % self.token


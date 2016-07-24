# -*- coding: utf-8 -*-
from app.database import DBManager
db = DBManager.db
class Article(db.Model):
    articleNumber = db.Column(db.Integer, unique=True, primary_key=True)
    title = db.Column(db.String(200))
    writer = db.Column(db.String(80))
    id = db.Column(db.String(200))
    content = db.Column(db.String(1000))
    writeDate = db.Column(db.String(50))
    imgName = db.Column(db.String(100))
    hits = db.Column(db.Integer)

    def __init__(self, title, writer, id, content, writeDate, imgName):
        self.title = title
        self.writer = writer
        self.id = id
        self.content = content
        self.writeDate = writeDate
        self.imgName = imgName
        self.hits = 0


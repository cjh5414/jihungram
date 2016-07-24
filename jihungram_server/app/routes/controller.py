#coding: utf-8
from app.blueprint import basic
from flask import jsonify, send_file, request, render_template
import os

IMAGE_FOLDER = os.path.join(os.path.abspath(os.path.dirname(__file__)) + "/../", 'image/')

@basic.route('/')
def index():
	return "hello jihun gram!!"

@basic.route('/data')
def data():
	data = {"names": ["John", "Jacob", "Julie", "Jennifer"]}
	from app.model.user import User
	admin = User.query.filter_by(username='admin').first()
	print (admin)
	return jsonify(data)

@basic.route('/imageDown/<fileName>')
def imageDown(fileName):
	return send_file(IMAGE_FOLDER+fileName, mimetype='image')

ALLOWED_EXTENSIONS = set(['png', 'jpg', 'jpeg', 'gif'])
def allowedFIle(filename):
	return '.' in filename and filename.rsplit('.', 1)[1] in ALLOWED_EXTENSIONS

@basic.route('/imageUp', methods=['post'])
def imageUp():
	file = request.files['imageFile']
	if file and allowedFIle(file.filename):
		file.save(IMAGE_FOLDER + file.filename)
		return "ok"
	return "error"

@basic.route('/requestPush')
def requestPush():
	from pushjack import GCMClient
	client = GCMClient(api_key='AIzaSyDPX9-HMIeRu5t0Tj9n8QlosB1tIRDdXNg')
	from app.model.user import User
	tokens = [u.token for u in User.query.all()]
	dic = {"title":"제목"}
	client.send(tokens, dic)
	return "push"

@basic.route('/hitsUp/<int:articleNum>')
def hitsUp(articleNum):
	from app.model.article import Article
	article = Article.query.filter_by(articleNumber=articleNum).first()
	article.hits += 1
	from app.database import DBManager
	DBManager.db.session.commit()
	return "hitsUp success"


@basic.route('/admin')
def admin():
	return render_template('index.html')

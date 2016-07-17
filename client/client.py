import socket
from tictactoe import logic as game0
import gui

class client():
	def join(self, game):
		pass
	def connect(self):
		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		s.connect((self.ip, self.port))
		s.setblocking(False)
		s.settimeout(60)
		try:
			#Get server version, do something if it does not fit
			server_version = int(s.recv(1024).decode())
			if server_version != self.version:
				print('[ERROR] Version missmatch')
				s.close()
				s = None
			else:
				#Send username
				s.send((self.name + '\n').encode())
		except ConnectionRefusedError:
			s.close()
			s = None
		return s
	def select_game(self, s):
		games = list(s.recv(1024).decode().split(';'))
		#clean the game entries
		for i in range(len(games)):
			games[i] = int(games[i])
		#recieve string of csv
		games_available = []
		games_available.append('Please select one of the following games:')
		for game in self.games:
			if game in games:
				games_available.append(str(game) + ' : ' + str(self.games[game]['title']))
		self.gui.setGame(games_available)
		self.gui.display()
		selection = None
		while selection == None or (not selection in games) or (not selection in self.games):
			if selection:
				prompt = 'Please select a valid game: '
			else:
				prompt = 'Please select a game: '
			selection = input(prompt)
			try:
				selection = int(selection)
			except ValueError:
				selection = -1
		return selection
		#schicke die id des gewaehlten spieles
	def join_game(self, s, selection):
		s.send((str(selection)+'\n').encode())
		code = s.recv(1024).decode().strip()
		print('[DEBUG] code:',code)
		if code == 'WAIT':
			self.gui.setGame(['Waiting for competition'])
			self.gui.display()
			code = s.recv(1024).decode().strip()
			if code == 'READY':
				game = self.games[selection]['handle'].logic(s, self.gui)
				game.handle()
		if code == 'READY':
			game = self.games[selection]['handle'].logic(s, self.gui)
			import pdb;pdb.set_trace()
			game.handle()	
		#Erwarte WAIT, READY, ERROR
		#Bei READY: YOURTURN/NOTYOURTURN
		#Bei erfolgreichem zug: Zughistory,
		#Ber wiederholtem versagen -1 und der gegner hat einen zug
		pass 
	def run(self):
		s = self.connect()
		selection = self.select_game(s)
		self.join_game(s, selection)
		
		#Send game ID
		while True:
			antwort = s.recv(1024)
			print('[{}] {}'.format(self.ip, antwort.decode()))
			n = input('Nachricht: ')
			n += '\n'
			s.send(n.encode())
		s.close()
	def __init__(self, port=4586, ip = 'localhost',username=''):
		self.games = {0:{'title':'TicTacToe','handle':game0}}
		self.port = port
		self.ip = ip
		self.gui = gui.Gui()
		self.version = 201607161730
		while username == '':
			username = input('Please enter a valid username: ')
		self.name = username

if __name__ == '__main__':
	c = client()
	c.run()

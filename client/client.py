import socket
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
		try:
			games = s.recv(1024).decode().split(';')
			games_available = []
			for game in self.games:
				if game[0] in games:
					games_available.append(game)
			self.gui.setGame(games_available)
			self.gui.display()
		except ConnectionRefusedError:
			s.close()
		#schicke die id des gewaehlten spieles
	def join_game(self, s):
		#Erwarte WAIT, READY, ERROR
		#Bei READY: YOURTURN/NOTYOURTURN
		#Bei erfolgreichem zug: Zughistory,
		#Ber wiederholtem versagen -1 und der gegner hat einen zug
		pass 
	def run(self):
		s = self.connect()
		self.select_game(s)
		
		#Send game ID
		while True:
			antwort = s.recv(1024)
			print('[{}] {}'.format(self.ip, antwort.decode()))
			n = input('Nachricht: ')
			n += '\n'
			s.send(n.encode())
		s.close()
	def __init__(self, port=4586, ip = 'localhost',username=''):
		self.games = {0:['TicTacToe']}
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

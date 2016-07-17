from tictactoe import graphic
class logic():
	def __init__(self,socket,gui):
		self.grid = [[0,0,0],[0,0,0],[0,0,0]]
		self.socket = socket
		self.graphic = graphic.grid()
		self.grid = self.graphic.grid
		self.gui = gui
	def check(self,player,x,y):
		 pass
	def handle(self):
		#expect usernames
		
		import pdb;pdb.set_trace()
		usernames = list(self.socket.recv(1024).decode().strip().split(';'))
		self.gui.setPlayers(usernames)
		turn = self.socket.recv(1024).decode().strip()
		if turn == 'YOURTURN':
			pass
		elif turn == 'NOTYOURTURN':
			pass
		else:
			pass#Error
		self.gui.setGame(self.grid)
		self.gui.display()

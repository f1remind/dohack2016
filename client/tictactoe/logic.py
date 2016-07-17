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
	def verify_input(self, inputstring):
		value = True
		try:
			x, y = inputstring.split()
			x = int(x)
			y = int(y)
			if x<0 or x>2 or y<0 or y>2:
				value = False
		except ValueError:
			value = False
		return value
				
	def handle(self):
		#expect usernames
		
		usernames = list(self.socket.recv(1024).decode().strip().split(';'))
		if len(usernames) > 1:
			if self.gui.players:
				self.graphic.insert('o',int(usernames[0]),int(usernames[1]))
			else:
				self.gui.setPlayers(usernames)
			turn = self.socket.recv(1024).decode().strip()
		else:
			turn = usernames[0]
		self.gui.setGame(self.grid)
		self.gui.display()			
		if turn == 'YOURTURN':
			value = self.verify_input('-1 -1')
			while not value: 
				print('enter the row and the column: (e.g. "1 1")')	
				inputstring = input('>>> ')
				value = self.verify_input(inputstring)
			self.socket.send((inputstring.split()[0] + ';' + inputstring.split()[1] + '\n').encode())
			code = self.socket.recv(1024).decode().strip()
			if code == 'YOURTURN':
				pass#You fucked up
			else:
				self.graphic.insert('x',int(inputstring.split()[0]),int(inputstring.split()[1]))
				self.gui.display()
				self.handle()
				
		elif turn == 'NOTYOURTURN':
			print('Waiting for opponent')
			self.handle()
		else:
			print('[ERROR] turn:',turn)
			pass#Error

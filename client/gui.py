#The gui has 4 fields
#One list of players on the left (uses 10 chars)
#One list with past actions on the right (uses 10 chars)
#A command prompt with explainations using two lines and a width of 80
#The actual game using 


class Gui():
	def debug(self):
		self.players =  ['Ben','Mike','Thomas','Jefferson','Longnameislongiton']
		#DEBUG
		from tictactoe import graphic
		g = graphic.grid()
		self.game = g.grid
	def __init__(self,players=[],history=[],prompt=[],game=[]):
		self.players = players
		self.history = history
		self.prompt = prompt
		self.game = game
	def setGame(self, game):
		self.game = game
	def setHistory(self, history):
		self.history = history
	def setPlayers(self, players):
		self.players = players
	def render(self):
		screen = ['']*24
		#Turn players into a string of max length of 8 and
		#20 entries
		players = ['|']*22 
		self.players.sort()
		for i in range(0, 22):
			if i < len(self.players):
				if len(self.players[i]) >= 8:
					name = self.players[i][:8]
				else:
					name = self.players[i]
					name += ' '*(8-len(self.players[i]))
				name += '|'
				players[i] += name
			else:
				players[i] += ' '*8 + '|'
		#Game should already be in string form
		game = ['']*22
		if len(self.game) <= 22:
			padding = (22 - len(self.game))//2
		else:
			padding = 0
		if self.game and len(self.game[0]) <= 60:
			vpadding = (60 - len(self.game[0]))//2
		else:
			vpadding = 0
		for i in range(22):
			if i <= padding or (i + padding) > 22:
				game[i] = ' '*60
			elif self.game and (i-padding) < len(self.game):
				if len(self.game[i-padding]) > 60:
					gamerow = self.game[(i-padding)][:60]
				else:
					gamerow = self.game[(i-padding)]
				game[i] += ' '*vpadding + gamerow + ' '*(60 - (vpadding + len(gamerow)))
			else:
				game[i] = ' '*60
		#Add the history
		#PLACEHOLDER
		history = ['|']*22
		for i in range(22):
			if i < len(self.history):
				if len(self.history[i]) >= 8:
					hist = self.history[i][:8]
				else:
					hist = self.history[i]
					padding = (8 - len(hist))//2
					hist = ' '*padding + hist + ' '*(8 - (padding + len(hist)))	
				history[i] = '|' + hist + '|'
			else:
				history[i] = '|        |'
				

		#Stitch the screen together
		for i in range(22):
			screen[i] = players[i]+game[i]+history[i]
		return screen					
	def render(self):
		screen = self.display()
		for line in screen:
			print(line)

if __name__ == '__main__':
	g = Gui()
	g.debug()
	g.display()

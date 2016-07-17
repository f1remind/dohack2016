import socket

class client():
	def run(self):
		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		s.connect((self.ip, self.port))
		try:
			#Get server version, do something if it does not fit
			server_version = s.recv(1024).decode()
			#Send username
			s.send((self.name + '\n').encode())
			#Get game list
			#recieve string of csv
			games_available = s.recv(1024).decode().split(';')
			print('[DEBUG] Games available:',*games_available)
			#Send game ID
			while True:
				antwort = s.recv(1024)
				print('[{}] {}'.format(self.ip, antwort.decode()))
				n = input('Nachricht: ')
				n += '\n'
				s.send(n.encode())
		finally:
			s.close()
	def __init__(self, port=4586, ip = 'localhost',username=''):
		self.port = port
		self.ip = ip
		self.version = 201607161730
		while username == '':
			username = input('Please enter a valid username: ')
		self.name = username

if __name__ == '__main__':
	c = client()
	c.run()

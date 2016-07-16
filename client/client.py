import socket

class client():
	def run(self):
		s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		s.connect((self.ip, self.port))
		try:
			print(s.recv(1024).decode())
			while True:
				n = input('Nachricht: ')
				n += '\n\r'
				s.send(n.encode())
				antwort = s.recv(1024)
				print('[{}] {}'.format(self.ip, antwort.decode()))
		finally:
			s.close()
	def __init__(self, port=4586, ip = 'localhost'):
		self.port = port
		self.ip = ip

if __name__ == '__main__':
	c = client()
	c.run()

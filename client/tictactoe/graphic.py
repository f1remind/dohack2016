# -*- coding: utf-8 -*-
#80x24
class grid():
	def generateGrid(self, width_entry, buffer_pixel):
	  dynamic_grid = []
	  dimensions = (width_entry + 2 * buffer_pixel) * 3 + 2
	  empty_row = ' ' * (width_entry + 2 * buffer_pixel) + '|' + ' ' * (width_entry + 2 * buffer_pixel) + '|' + ' ' * (width_entry + buffer_pixel * 2)
	  seperator_row = '-'*dimensions
	  dynamic_grid += [empty_row] * (width_entry + 2*buffer_pixel) + [seperator_row] + [empty_row] * (width_entry + 2*buffer_pixel) + [seperator_row] + [empty_row] * (width_entry + 2*buffer_pixel)
	  return dynamic_grid
	def __init__(self, width_entry=3, buffer_pixel=1):
		self.width_entry = width_entry
		self.buffer_pixel = buffer_pixel
		self.grid = self.generateGrid(width_entry, buffer_pixel)
		self.o = [['O'],['/\\', '\\/'],[' _ ','(_)','   '],[' __ ','/  \\','|  |','\\__/'],['  _  ',' / \\ ','(   )',' \\_/ ','     ']]
		self.x = [['x'],['\\/','/\\'],['\ /',' X ', '/ \\'],['\\  /',' \\/ ',' /\\ ','/  \\'],['\\   /', ' \\ / ', '  X  ', ' / \\ ', '/   \\']]
	def insert(self, sign, x, y):
		pos_x = (self.buffer_pixel * 2 + self.width_entry + 1)*x + self.buffer_pixel
		pos_y = (self.buffer_pixel * 2 + self.width_entry + 1)*y + self.buffer_pixel
		if sign == 'x':
			sign = self.x
		if sign == 'o':
			sign = self.o
		for i in range(self.width_entry):
			row = list(self.grid[pos_x + i])
			for k in range(self.width_entry):
				row[pos_y + k] = sign[self.width_entry - 1][i][k]
			self.grid[pos_x + i] = ''
			for char in row:
				self.grid[pos_x + i] += char
if __name__ == '__main__':
	g = grid(buffer_pixel = 1, width_entry=3)
	for sign in g.x:
		for row in sign:
			print(row)
	for sign in g.o:
		for row in sign:
			print(row)
	for line in g.grid:print(line)
	g.insert('x',1,1)
	for line in g.grid:print(line)
	g.insert('o',0,0)
	for line in g.grid:print(line)
	g.insert('x',2,2)
	for line in g.grid:print(line)
	g.insert('o',1,0)
	for line in g.grid:
		print(line)

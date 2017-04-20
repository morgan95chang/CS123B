import sys
import os

output_line_append_1 = "_SECTION_1"
output_line_append_2 = "_SECTION_2"

def separate(input_file, index):
	f = open(input_file, 'r')
	filename = input_file.split(".", 1)[0]
	filetype = input_file.split(".", 1)[1]
	o1_filename = filename + output_line_append_1 + '.' + filetype
	o2_filename = filename + output_line_append_2 + '.' + filetype
	o1 = open(o1_filename, 'w')
	o2 = open(o2_filename, 'w')
	temp = ""
	for line in f:
		if(line[0] == ">"):
			if(temp != ""):
				o1.write(temp[:index] + "\n")
				print(temp[:index] + "\n")
				o2.write(temp[index:] + "\n")
				print(temp[index:] + "\n")
			temp = ""
			#o1.write(line + "\n")
			o1.write(line)
			#o2.write(line + "\n")
			o2.write(line)
			print(line + "\n")
		else:
			temp = temp + line	
	o1.write(temp[:index] + "\n")
	print(temp[:index] + "\n")
	o2.write(temp[index:] + "\n")
	print(temp[index:] + "\n")
	temp = ""


if __name__ == '__main__':
  #data_file = sys.argv[1]
  input_file = input("Please enter the EXACT file name of your .fa or .txt file \n")
  #print(input_file)
  index = int(input("Please enter the index to clip the file at: \n"))
  #print(index)
  separate(input_file, index)
## Pencil Durability - Commands

Format: (Terminal >) [Command] [Params]

Test	- no params
		- will run tests from PencilTest.java
		
Write	- param1: String
		- will write the param string onto the paper
		
Sharpen	- no params
		- will attempt to sharpen the point, increasing point durability
		
Erase	- param1: String
		- will erase last occurance of the param string from the paper
		
Edit	- param1: String
		- will attempt to insert param string into the location of the last erased text
		
Print	- no params
		- will display in the console text currently on the page
		
ClearPage	- no params
			- will remove all text and erase data from the paper
			

Completion: "[Command] complete" should display in the console

# Setup

For this programming kata I used Java and Visual Studio Code as my IDE.
If you do the same you will want to install the extension "Java Extension Pack". It has everything you will need to run Java.
Dependancies should be already setup to use a hard reference.
pencil/.vscode/lanuch.json contains two debug launch configurations for VS Code. One is reciving arguments upon launch, the other is hard set with the argument "Test".
workspace1.code-workspace is a VS Code workspace file. Needs to be present with VS Code or extensions will throw workspace related errors.

# Print
*As a writer  
I want to be able to see what is written on the my paper  
so that I can better review my thoughts*  

When the pencil is instructed to print the currently held text will be display in the console.

# ClearPage
*As a writer  
I want to be able to write on a new piece of paper  
so that I can better organize my thoughts*  

When the pencil is instructed to clear page text and erase data will both be reset.
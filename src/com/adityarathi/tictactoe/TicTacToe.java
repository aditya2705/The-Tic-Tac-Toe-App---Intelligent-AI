package com.adityarathi.tictactoe;

public class TicTacToe {
	
	char[][] tictactoegrid = new char[3][3];
	
	int _movesmade;
	
	int _count;
		
	char winner;

		
	public TicTacToe()
		
	{
			
	reset();
		
	}

		
		
	public void reset()
		
	{
			
	_movesmade = 0;
			
	winner = ' ';
			

	for (int i = 0; i < 3; i++)
				
	for (int j = 0; j < 3; j++)
					

	tictactoegrid[i][j] = ' ';
		
	}
	
	
	public void setTurn()
	{
		_movesmade++;
	}
	
		
	public char whoseTurnfirstX()
		
	{
			
	if (_movesmade % 2 == 1)
				
	return 'O';
			
	return 'X';
		
	}
	
	public char whoseTurnfirstO()
	
	{
			
	if (_movesmade % 2 == 1)
				
	return 'X';
			
	return 'O';
		
	}
		

		
	public char getValue(int row, int column) throws Exception
		
	{

			
	if ( ( column > 2 || column < 0 ) || ( row > 2 || row < 0 ) )
				
	throw new Exception("Out of scope");
			
	return tictactoegrid[row][column];
		
	}
		

		
	public void playMoveFirstX(int row, int column) throws Exception
		
	{
		
	if ( this.getValue(row, column) != ' ')
				
	throw new Exception("Don't embarass yourself");


			
	tictactoegrid[row][column] = this.whoseTurnfirstX();
			
	_movesmade++;
		
		
	this.theWinner();
		
	}
	public void playMoveFirstO(int row, int column) throws Exception
	
	{
		
	if ( this.getValue(row, column) != ' ')
				
	throw new Exception("Don't embarass yourself");


			
	tictactoegrid[row][column] = this.whoseTurnfirstO();
			
	_movesmade++;
		
		
	this.theWinner();
		
	}
		
		
	public char theWinner()
		
	{
			
	
		
		
	if (winner != ' ') return winner;

			
	
			
	if ( (tictactoegrid[0][0] == tictactoegrid[1][1] && 
				  
	tictactoegrid[0][0] == tictactoegrid[2][2] && tictactoegrid[0][0] != ' ') ||
				  
	(tictactoegrid[2][0] == tictactoegrid[1][1] && 
				 
	 tictactoegrid[2][0] == tictactoegrid[0][2] && tictactoegrid[2][0] != ' ') )

			
	{
				
	winner = tictactoegrid[1][1];
				
	return winner;
			
	}
		
		
			
	
		
		
	for (int i = 0; i < 3; i++)
	{
		
		if (tictactoegrid[i][0] == tictactoegrid[i][1] &&
						
		tictactoegrid[i][0] == tictactoegrid[i][2] && tictactoegrid[i][0] != ' ')

					
		{
						
		winner = tictactoegrid[i][0];
						
		return winner;
					
		}

					
		else if (tictactoegrid[0][i] == tictactoegrid[1][i] &&
						
		tictactoegrid[0][i] == tictactoegrid[2][i] && tictactoegrid[0][i] != ' ')
					
		{
						
		winner = tictactoegrid[0][i];
						
		return winner;
					
		}

				
		}

			
			
	for(int i = 0; i < 3; i++)
	for (int j = 0; j < 3; j++)
				
	{
					
	if (tictactoegrid[i][j] == ' ')
						
	return winner;
				
	}
			
	return 'D';
		
	}
	}


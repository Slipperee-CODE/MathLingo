package com.caischeidler.models;

public class ProblemHandler {
	private Guess[] guesses;
	private Problem problem;
	private int maxGuessesAllowed;
	private int guessIndex = 0;
	private boolean win = false;
	
	public ProblemHandler(Problem problem, int maxGuessesAllowed) {
		super();
		this.guesses = new Guess[maxGuessesAllowed];
		this.problem = problem;
		this.maxGuessesAllowed = maxGuessesAllowed;
	}

	public void handleCurrGuess(Guess guess) {
		guesses[guessIndex] = colorGuess(guess, problem);
		win = checkForWin(guesses[guessIndex], problem);
		guessIndex++;
	}
	
	private Guess colorGuess(Guess guess, Problem problem) {
		//check for green first
		for (int i = 0; i < problem.getGuessBoxSolutions().length; i++) {
			if (guess.getGuessBoxes()[i].getValue().equals(problem.getGuessBoxSolutions()[i])){
				guess.getGuessBoxes()[i].setColor(GuessBox.GREEN);
			}
		}
		
		//nested for loop to check for yellow except any already green ones in solution and problem get skipped to check for yellows
		for (int i = 0; i < problem.getGuessBoxSolutions().length; i++) {
			if (guess.getGuessBoxes()[i].getColor().equals(GuessBox.GRAY)) {
				int yellowForIndex = matchingCurrentlyGrayBox(guess, i ,problem);
				if (yellowForIndex > -1) {
					if (noYellowForSolutionAtPreviousIndex(guess, i , problem, yellowForIndex)) {
						guess.getGuessBoxes()[i].setColor(GuessBox.YELLOW);
					}
				}
			}
		}
		return guess;
	}
	
	private int matchingCurrentlyGrayBox(Guess guess, int currIndex, Problem problem) {
		for (int i = 0; i < guess.getGuessBoxes().length; i++) {
			if (guess.getGuessBoxes()[i].getColor().equals(GuessBox.GRAY) && guess.getGuessBoxes()[currIndex].getValue().equals(problem.getGuessBoxSolutions()[i])){
				return i;
			}
		}
		return -1;
	}
	
	private boolean noYellowForSolutionAtPreviousIndex(Guess guess, int currIndex, Problem problem, int yellowForIndex) {
		for (int i = currIndex; i >= yellowForIndex; i--) {
			if (guess.getGuessBoxes()[i].getColor().equals(GuessBox.YELLOW) && guess.getGuessBoxes()[i].getValue().equals(problem.getGuessBoxSolutions()[yellowForIndex])){
				return false;
			}
		}
		return true;
	}
	
	private boolean checkForWin(Guess guess, Problem problem) {
		for (int i = 0; i < problem.getGuessBoxSolutions().length; i++) {
			if (!guess.getGuessBoxes()[i].getColor().equals(GuessBox.GREEN)){
				return false;
			}
		}
		return true;
	}

	public Guess[] getGuesses() {
		return guesses;
	}

	public void setGuesses(Guess[] guesses) {
		this.guesses = guesses;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public int getMaxGuessesAllowed() {
		return maxGuessesAllowed;
	}

	public void setMaxGuessesAllowed(int maxGuessesAllowed) {
		this.maxGuessesAllowed = maxGuessesAllowed;
	}

	public int getGuessIndex() {
		return guessIndex;
	}

	public void setGuessIndex(int guessIndex) {
		this.guessIndex = guessIndex;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}
}

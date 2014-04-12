/**
 * @author J. Bailey
 * @date 22 Feb '14
 * @brief This controls the flow of the game, specific to Othello.
 * This controls how the game works when the user selects to play Othello,
 * such as when a flip is possible, and when a win is detected.
 *
 */

public class OthelloGameLogic extends AbstractGameImplementation {
	/**
	 * Method for placing a piece on the board
	 * @param x			The x position the Piece is placed on the board.
	 * @param y			The y position the Piece is placed on the board.
	 * @param player	the Player that is setting the piece.
	*/
	public void setPiece(int x, int y, AbstractPlayer player) {
        if (checkValid(x, y, player) == true) {
			String playerColour = player.getColour();

            getBoard().setPiece(new Piece(playerColour), x, y);

            flipNorth(x, y, player);
            flipSouth(x, y, player);
            flipWest(x, y, player);
            flipEast(x, y, player);
            flipNorthWest(x, y, player);
            flipNorthEast(x, y, player);
            flipSouthWest(x, y, player);
            flipSouthEast(x, y, player);
        }
    }

  	public void setPiece(int x, int y, AbstractPlayer player, boolean load) {
  		if (load){
  			String playerColour = player.getColour();
        getBoard().setPiece(new Piece(playerColour), x, y);
      }
    }

	/**
	 * Creates the board, then place the starting pieces in the centre
	 * of the board.
	 * @param width		The width of the board you would like to create.
	 * @param height	The height of the board you would like to create.
	 * @return null
	 */
    public void setBoard(int width, int height) {
        super.setBoard(width, height);
        // (3,3) is the position this Piece is placed on the board
        getBoard().setPiece(new Piece(WHITE_PIECE),(width/DIVIDED_BY_TWO)-1,(height/DIVIDED_BY_TWO)-1);
        // (3,4) is the position this Piece is placed on the board
        getBoard().setPiece(new Piece(BLACK_PIECE),(width/DIVIDED_BY_TWO)-1,(height/DIVIDED_BY_TWO));
        // (4,3) is the position this Piece is placed on the board
        getBoard().setPiece(new Piece(BLACK_PIECE),(width/DIVIDED_BY_TWO),(height/DIVIDED_BY_TWO)-1);
        // (4,4) is the position this Piece is placed on the board
        getBoard().setPiece(new Piece(WHITE_PIECE),(width/DIVIDED_BY_TWO),(height/DIVIDED_BY_TWO));
    }

	/**
	 * Finds the piece at a certain position (x,y) on the board.
	 * @param x			The x position of the Piece which you would like returned.
	 * @param y 		The y position of the Piece which you would like returned.
	 * @return Piece	The Piece which is found at position (x,y) on the Board.
	 */
	public Piece getPiece(int x, int y) {
		if (getBoard().isEmpty(x,y) == false) {
			return getBoard().getBoard()[x][y];
		} else {
			return new Piece("");
		}
	}

	/**
	 * Finds the winner of the game
	 * @return playerNumber This returns an integer representing who won the game.
	 * 1 means Player 1 has won, 2 means Player 2 has won, 3 means a draw, -1 means the game
	 * hasn't been won yet.
	 */
	public int getWinner() {
		int totalPieces[] = new int[NUMBER_OF_PLAYERS];
		if (checkWin() == true) {
			for (int i = 0; i < BOARD_WIDTH; i++) {
				for (int j = 0; j < BOARD_WIDTH; j++) {
                    if (getPiece(i,j).getColour().equals(getPlayer(0).getColour())) {
                        totalPieces[0]++;
                    }else if (getPiece(i,j).getColour()
											.equals(getPlayer(1).getColour())) {
						totalPieces[1]++;
					}
				}
			}
			/* The winner is the player with the most pieces */
			if (totalPieces[0] > totalPieces[1]) {
				return PLAYER_ONE_WIN;
			} else if (totalPieces[1] > totalPieces[0]) {
				return PLAYER_TWO_WIN;
			} else {
				return DRAW;
			}
		} else {
			return CONTINUE_GAME;
		}
	}

	/**
	 * Finds the colour of the player that wasn't passed in.
	 * @param player	The Player you don't want the colour for
	 * @return colour	The colour of the opponent to the Player passed in.
	 */
	private String getOtherPlayerColour(AbstractPlayer player) {
		if (getPlayer(0) != player) {
			return getPlayer(0).getColour();
		} else {
			return getPlayer(1).getColour();
		}
	}

	/** Constructor for Othello */
	public OthelloGameLogic() {
        this.setBoard(BOARD_WIDTH, BOARD_HEIGHT);
        getPlayer(0).setColour("Black");
        getPlayer(1).setColour("White");
	}

	/**
	 * Checks if it is possible for a Player to take their turn
	 * @param player		The Player you want to check if it is possible to take a turn for.
	 * @return takeable		This will return true if a Player is able to take a turn.
	 */
	public boolean checkTakeableTurn(AbstractPlayer player) {
		boolean takeable = false;
		for (int i = 0; i < BOARD_WIDTH; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				if (getBoard().isEmpty(i,j) == true) {
					if (takeable == false) takeable = checkFlip(i, j, player);
				}
			}
		}
		System.out.println("Takeable: " + takeable);
		return takeable;
	}

	/**
	 * Checks if a Player has won the game
	 * @return gameComplete		returns true if the game is over, or false if the game is not over.
	 */
    public boolean checkWin(){
		boolean boardFull = true;
		boolean validMoves = false;

		for (int i = 0; i < BOARD_WIDTH; i++) {
			for (int j = 0; j < BOARD_HEIGHT; j++) {
				if (getBoard().isEmpty(i,j)) {
					boardFull = false;
				}
			}
		}

		if (boardFull == true) {
			return true;
		}

		for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                for (int k = 0; k < NUMBER_OF_PLAYERS; k++) {
                // Only check for the player who's current turn it is.
                // Skip turn if no valid move.
                    if(checkFlip(i,j,getPlayer(k)) == true) validMoves = true;
                }
            }
        }
		if (validMoves == false) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if a Piece above the Player will be
	 * flipped if a Piece is placed at (x,y)
	 * @param x			The x position of the Piece you want to check if there are flips above of.
	 * @param y			The y position of the Piece you want to check if there are flips above of.
	 * @param player	The Player which you are checking if there are checking if there are flips above for.
	 * @return flip		Returns if there is a Piece that will be flipped for the Player above the specified position.
	 */
	private boolean checkFlipNorth(int x, int y, AbstractPlayer player) {
		String playerColour = player.getColour();
		String otherPlayerColour = getOtherPlayerColour(player);

		boolean validity = false;
		boolean partValid = false;

		if (y - 1 >= 0) {
			if (getPiece(x,y-1).getColour().equals(otherPlayerColour)) {
				partValid = true;
			}
			for (int i = y - 1; i >= 0; i--) {
				if (getBoard().isEmpty(x,i) == true) {
					partValid = false;
				}
				if (getPiece(x,i).getColour().equals(playerColour)
					&& partValid == true) {
					validity = true;
				}
			}
		}
		return validity;
	}

	/**
	 * Checks if a Piece below the Player will be
	 * flipped if a Piece is placed at (x,y)
	 * @param x			The x position of the Piece you want to check if there are
	 *  flips below of.
	 * @param y			The y position of the Piece you want to check if there are
	 *  flips below of.
	 * @param player	The Player which you are checking if there are checking if
	 *  there are flips below for.
	 * @return flip		Returns if there is a Piece that will be flipped for the
	 *  Player below the specified position.
	 */
	private boolean checkFlipSouth(int x, int y, AbstractPlayer player) {
		String playerColour = player.getColour();
       	String otherPlayerColour = getOtherPlayerColour(player);

		boolean validity = false;
		boolean partValid = false;

		if (y + 1 < BOARD_HEIGHT) {
			if (getPiece(x,y+1).getColour().equals(otherPlayerColour)) {
				partValid = true;
			}
			for (int i = y + 1; i < BOARD_HEIGHT; i++) {
				if (getBoard().isEmpty(x,i) == true) {
					partValid = false;
				}
				if (getPiece(x,i).getColour().equals(playerColour)
					&& partValid) {
					validity = true;
				}
			}
		}
		return validity;
	}

	/**
	 * Checks if a Piece left of the Player will be
	 * flipped if a Piece is placed at (x,y)
	 * @param x			The x position of the Piece you want to check if there are
	 *  flips left of.
	 * @param y			The y position of the Piece you want to check if there are
	 *  flips left of.
	 * @param player	The Player which you are checking if there are checking if
	 *  there are flips to the left for.
	 * @return flip		Returns if there is a Piece that will be flipped for the
	 * Player left of the specified position.
	 */
	private boolean checkFlipWest(int x, int y, AbstractPlayer player) {
        String playerColour = player.getColour();
		String otherPlayerColour = getOtherPlayerColour(player);

        boolean validity = false;
        boolean partValid = false;

        if (x - 1 >= 0) {
            if (getPiece(x-1,y).getColour().equals(otherPlayerColour)) {
                partValid = true;
            }
            for (int i = x - 1; i >= 0; i--) {
                if (getBoard().isEmpty(i,y)) {
                    partValid = false;
                }
                if (getPiece(i,y).getColour().equals(playerColour)
					&& partValid == true) {
                    validity = true;
                }
            }
        }
        return validity;
	}

	/**
	 * Checks if a Piece right of the Player will be
	 * flipped if a Piece is placed at (x,y)
	 * @param x			The x position of the Piece you want to check if there are
	 *  flips right of.
	 * @param y			The y position of the Piece you want to check if there are
	 *  flips right of.
	 * @param player	The Player which you are checking if there are checking if
	 *  there are flips to the right for.
	 * @return flip		Returns if there is a Piece that will be flipped for the
	 * player right of the specified position.
	 */
	private boolean checkFlipEast(int x, int y, AbstractPlayer player) {
		String playerColour = player.getColour();
        String otherPlayerColour = getOtherPlayerColour(player);

		boolean validity = false;
		boolean partValid = false;

		if (x + 1 < BOARD_WIDTH) {
			if (getPiece(x+1,y).getColour().equals(otherPlayerColour)) {
				partValid = true;
			}
			for (int i = x + 1; i < BOARD_WIDTH; i++) {
				if (getBoard().isEmpty(i,y) == true) {
					partValid = false;
				}
				if (getPiece(i,y).getColour().equals(playerColour)
					&& partValid == true) {
					validity = true;
				}
			}
		}
        return validity;
	}

	/**
	 * Checks if a Piece above and left of the Player will be
	 * flipped if a Piece is placed at (x,y)
	 * @param x			The x position of the Piece you want to check if there are
	 *  flips above and left of.
	 * @param y			The y position of the Piece you want to check if there are
	 *  flips left of.
	 * @param player	The Player which you are checking if there are checking if
	 *  there are flips right of for.
	 * @return flip		Returns if there is a Piece that will be flipped for the
	 * player above and left of the specified position.
	 */
	private boolean checkFlipNorthWest(int x, int y, AbstractPlayer player) {
		String playerColour = player.getColour();
		String otherPlayerColour = getOtherPlayerColour(player);

		boolean validity = false;
		boolean partValid = false;

		if (x - 1 >= 0 && y - 1 >= 0) {
			if (getPiece(x-1,y-1).getColour().equals(otherPlayerColour)) {
				partValid = true;
			}
			int i = x - 1;
			int j = y - 1;
			while (i >= 0 && j >= 0) {
				if (getBoard().isEmpty(i,j) == true) {
					partValid = false;
				}
				if (getPiece(i,j).getColour().equals(playerColour)
					&& partValid == true) {
					validity = true;
				}
				i--;
				j--;
			}
		}
		return validity;
	}

	/**
	 * Checks if a Piece above and right of the Player will be
	 * flipped if a Piece is placed at (x,y)
	 * @param x			The x position of the Piece you want to check if there are
	 *  flips above and right of.
	 * @param y			The y position of the Piece you want to check if there are
	 *  flips right of.
	 * @param player	The Player which you are checking if there are checking if
	 *  there are flips right of for.
	 * @return flip		Returns if there is a Piece that will be flipped for the
	 * Player above and right of the specified position.
	 */
	private boolean checkFlipNorthEast(int x, int y, AbstractPlayer player) {
		String playerColour = player.getColour();
        String otherPlayerColour = getOtherPlayerColour(player);

		boolean validity = false;
		boolean partValid = false;

		if (x + 1 < BOARD_WIDTH && y - 1 >= 0) {
			if (getPiece(x+1,y-1).getColour().equals(otherPlayerColour)) {
				partValid = true;
			}
			int i = x + 1;
			int j = y - 1;
			while (i < BOARD_WIDTH && j >= 0) {
				if (getBoard().isEmpty(i,j) == true) {
					partValid = false;
				}
				if (getPiece(i,j).getColour().equals(playerColour)
					&& partValid == true) {
					validity = true;
				}
				i++;
				j--;
			}
		}
		return validity;
	}

	/**
	 * Checks if a Piece below and right of the Player will be
	 * flipped if a Piece is placed at (x,y)
	 * @param x			The x position of the Piece you want to check if there are
	 *  flips below and right of.
	 * @param y			The y position of the Piece you want to check if there are
	 *  flips below and right of.
	 * @param player	The Player which you are checking if there are checking if
	 *  there are flips right of for.
	 * @return flip		Returns if there is a Piece that will be flipped for the
	 * Player above and right of the specified position.
	 */
	private boolean checkFlipSouthEast(int x, int y, AbstractPlayer player) {
		String playerColour = player.getColour();
        String otherPlayerColour = getOtherPlayerColour(player);

		boolean validity = false;
		boolean partValid = false;

		if (x + 1 < BOARD_WIDTH && y + 1 < BOARD_HEIGHT) {
			if (getPiece(x+1,y+1).getColour().equals(otherPlayerColour)) {
				partValid = true;
			}
			int i = x + 1;
			int j = y + 1;
			while (i < BOARD_WIDTH && j < BOARD_HEIGHT) {
				if (getBoard().isEmpty(i,j) == true) {
					partValid = false;
				}
				if (getPiece(i,j).getColour().equals(playerColour)
					&& partValid == true) {
					validity = true;
				}
				i++;
				j++;
			}
		}
		return validity;
	}

	/**
	 * Checks if a Piece below and left of the Player will be
	 * flipped if a Piece is placed at (x,y)
	 * @param x			The x position of the Piece you want to check if there are
	 *  flips below and left of.
	 * @param y			The y position of the Piece you want to check if there are
	 *  flips below and left of.
	 * @param player	The Player which you are checking if there are checking if
	 *  there are flips right of for.
	 * @return flip		Returns if there is a Piece that will be flipped for the
	 * Player above and left of the specified position.
	 */
	private boolean checkFlipSouthWest(int x, int y, AbstractPlayer player) {
		String playerColour = player.getColour();
        String otherPlayerColour = getOtherPlayerColour(player);

        boolean validity = false;
        boolean partValid = false;

        if (x - 1 >= 0 && y + 1 < BOARD_HEIGHT) {
            if (getPiece(x-1,y+1).getColour()
						   .equals(otherPlayerColour)) {
                partValid = true;
            }
            int i = x - 1;
            int j = y + 1;
            while (i >= 0 && j < BOARD_HEIGHT) {
                if (getBoard().isEmpty(i,j) == true) {
                    partValid = false;
                }
                if (getPiece(i,j).getColour().equals(playerColour)
					&& partValid == true) {
                    validity = true;
                }
                i--;
                j++;
            }
        }
		return validity;
    }

	/**
	 * Checks if any Pieces will be flipped if a Piece is placed at (x,y)
	 * @param x				The x position of the Piece you want to check if there are
	 *	Pieces that will be flipped if placed there.
	 * @param y				The y position of the Piece you want to check if there are
	 *	Pieces that will be flipped if placed there
	 * @param AbstractPlayer		The Player which you are checking if there are checking if
	 *  there are flips for.
	 * @return flip			Returns true if there is a Piece that will be flipped for the
	 *  Player at the specified position.
	 */
	private boolean checkFlip(int x, int y, AbstractPlayer player) {
		boolean validity = false;

		// Checking if there is a flip above the selected position
		if (validity == false) {
			validity = checkFlipNorth(x, y, player);
		}

		// Checking if there is a flip below the selected position
		if (validity == false) {
			validity = checkFlipSouth(x, y, player);
		}

		// Checking if there is a flip left of the selected position
		if (validity == false) {
			validity = checkFlipWest(x, y, player);
		}

		// Checking if there is a flip right of the selected position
		if (validity == false) {
			validity = checkFlipEast(x, y, player);
		}

		// Checking if there is a flip above and left of the selected position
		if (validity == false) {
			validity = checkFlipNorthWest(x, y, player);
		}

		// Checking if there is a flip above and right of the selected position
		if (validity == false) {
			validity = checkFlipNorthEast(x, y, player);
		}

		// Checking if there is a flip below and right of the selected position
		if (validity == false) {
			validity = checkFlipSouthEast(x, y, player);
		}

		// Checking if there is a flip below and left of the selected position
		if (validity == false) {
        	validity = checkFlipSouthWest(x, y, player);
		}

		return validity;
	}

	/**
	 * Checks if a move will be valid is a piece is place at (x,y) for a
	 * specified player.
	 * @param x			The x position which you want to check if a move is
	 *  valid for.
	 * @param y			The y position which you want to check if a move is
	 *  valid for.
	 * @param player	The Player which you want to check a move is valid for.
	 * @return valid	This will return true if a move is valid for a Player at
	 *  that position.
	 */
	public boolean checkValid(int x, int y, AbstractPlayer player){
		if (getBoard().isEmpty(x,y) == true) {
			return checkFlip(x,y,player);
		} else {
			return false;
		}
	}

	/**
	 * Flips all pieces above the placed piece that will
	 * should be flipped
	 * @param x			The x position which the piece which causes a flip is placed at.
	 * @param y			The y position which the piece which causes a flip is placed at.
	 * @param player	The player which causes the flip.
	 * @return null
	 */
	private void flipNorth(int x, int y, AbstractPlayer player) {
		if (checkFlipNorth(x, y, player) == true) {
			String playerColour = player.getColour();
            String otherPlayerColour = getOtherPlayerColour(player);

			boolean flipping = true;
			int i = y - 1;
			while (i >= 0 && flipping == true) {
				if (getPiece(x,i).getColour().equals(otherPlayerColour)) {
					getBoard().getBoard()[x][i].changeColour(playerColour);
				} else {
					flipping = false;
				}
				i--;
			}
		}
	}

	/**
	 * Flips all pieces below the placed piece that will
	 * should be flipped
	 * @param x			The x position which the Piece which causes a flip is placed at.
	 * @param y			The y position which the Piece which causes a flip is placed at.
	 * @param player	The player which causes the flip.
	 * @return null
	 */
	private void flipSouth(int x, int y, AbstractPlayer player) {
		if (checkFlipSouth(x, y, player) == true) {
			String playerColour = player.getColour();
            String otherPlayerColour = getOtherPlayerColour(player);

			boolean flipping = true;
			int i = y + 1;
			while (i < BOARD_HEIGHT && flipping == true) {

				if (getPiece(x,i).getColour().equals(otherPlayerColour)) {
					getBoard().getBoard()[x][i].changeColour(playerColour);
				} else {
					flipping = false;
				}
				i++;
			}
		}
	}

	/**
	 * Flips all pieces left of the placed piece that will
	 * should be flipped
	 * @param x			The x position which the Piece which causes a flip is placed at.
	 * @param y			The y position which the Piece which causes a flip is placed at.
	 * @param player	The player which causes the flip.
	 * @return null
	 */
	private void flipWest(int x, int y, AbstractPlayer player) {
		if (checkFlipWest(x, y, player) == true) {
			String playerColour = player.getColour();
            String otherPlayerColour = getOtherPlayerColour(player);

			boolean flipping = true;
			int i = x - 1;
			while (i >= 0 && flipping == true) {
				if (getPiece(i,y).getColour().equals(otherPlayerColour)) {
					getBoard().getBoard()[i][y].changeColour(playerColour);
				} else {
					flipping = false;
				}
				i--;
			}
		}
	}

	/**
	 * Flips all pieces left of the placed piece that will
	 * should be flipped
	 * @param x			The x position which the Piece which causes a flip is placed at.
	 * @param y			The y position which the Piece which causes a flip is placed at.
	 * @param player	The player which causes the flip.
	 * @return null
	 */
	private void flipEast(int x, int y, AbstractPlayer player) {
		if (checkFlipEast(x, y, player) == true) {
			String playerColour = player.getColour();
            String otherPlayerColour = getOtherPlayerColour(player);

			boolean flipping = true;
			int i = x + 1;
			while (i < BOARD_WIDTH && flipping == true) {
				if (getPiece(i,y).getColour().equals(otherPlayerColour)) {
					getBoard().getBoard()[i][y].changeColour(playerColour);
				} else {
					flipping = false;
				}
				i++;
			}
		}
	}

	/**
	 *Flips all pieces above and left of the placed piece
	 * that should be flipped
	 * @param x			The x position which the Piece which causes a flip is placed at.
	 * @param y			The y position which the Piece which causes a flip is placed at.
	 * @param player	The player which causes the flip.
	 * @return null
	 */
	private void flipNorthWest(int x, int y, AbstractPlayer player) {
		if (checkFlipNorthWest(x, y, player) == true) {
			String playerColour = player.getColour();
            String otherPlayerColour = getOtherPlayerColour(player);

			boolean flipping = true;
			int i = x - 1;
			int j = y - 1;
			while (i >= 0 && flipping == true) {

				if (getPiece(i,j).getColour().equals(otherPlayerColour)) {
					getBoard().getBoard()[i][j].changeColour(playerColour);
				} else {
					flipping = false;
				}
				i--;
				j--;
			}
		}
	}

	/**
	 * Flips all pieces above and right of the placed piece
	 * that should be flipped
	 * @param x			The x position which the Piece which causes a flip is placed at.
	 * @param y			The y position which the Piece which causes a flip is placed at.
	 * @param player	The player which causes the flip.
	 * @return null
	 */
	private void flipNorthEast(int x, int y, AbstractPlayer player) {
		if (checkFlipNorthEast(x, y, player) == true) {
			String playerColour = player.getColour();
            String otherPlayerColour = getOtherPlayerColour(player);

			boolean flipping = true;
			int i = x + 1;
			int j = y - 1;
			while (j >= 0 && flipping == true) {
				if (getPiece(i,j).getColour().equals(otherPlayerColour)) {
					getBoard().getBoard()[i][j].changeColour(playerColour);
				} else {
					flipping = false;
				}
				i++;
				j--;
			}
		}
	}

	/**
	 * Flips all pieces below and right of the placed piece
	 * that should be flipped
	 * @param x			The x position which the Piece which causes a flip is placed at.
	 * @param y			The y position which the Piece which causes a flip is placed at.
	 * @param player	The player which causes the flip.
	 * @return null
	 */
	private void flipSouthEast(int x, int y, AbstractPlayer player) {
		if (checkFlipSouthEast(x, y, player) == true) {
			String playerColour = player.getColour();
            String otherPlayerColour = getOtherPlayerColour(player);

			boolean flipping = true;
			int i = x + 1;
			int j = y + 1;
			while (j < BOARD_HEIGHT && flipping == true) {
				if (getPiece(i,j).getColour().equals(otherPlayerColour)) {
					getBoard().getBoard()[i][j].changeColour(playerColour);
				} else {
					flipping = false;
				}
				i++;
				j++;
			}
		}
	}

	/**
	 * Flips all pieces above and left of the placed piece
	 * that should be flipped
	 * @param x			The x position which the Piece which causes a flip is placed at.
	 * @param y			The y position which the Piece which causes a flip is placed at.
	 * @param player	The player which causes the flip.
	 * @return null
	 */
	private void flipSouthWest(int x, int y, AbstractPlayer player) {

		if (checkFlipSouthWest(x, y, player) == true) {
			String playerColour = player.getColour();
            String otherPlayerColour = getOtherPlayerColour(player);

			boolean flipping = true;
			int i = x - 1;
			int j = y + 1;
			while (i >= 0 && flipping == true) {

				if (getPiece(i,j).getColour().equals(otherPlayerColour)) {
					getBoard().getBoard()[i][j].changeColour(playerColour);
				} else {
					flipping = false;
				}
				i--;
				j++;
			}
		}
	}

  public static void main(String[] args) {
    final int TOO_BIG = 42;
    final int TOO_SMALL = -42;
    final int ROW_SIX = 6;

		OthelloGameLogic othelloGameLogic = new OthelloGameLogic();
		AbstractPlayer testPlayer = new Human();
		testPlayer.setColour("Black");

		othelloGameLogic.setPiece(0, 0, testPlayer);
		if(othelloGameLogic.getBoard().getBoard()[0][ROW_SIX].getColour().equals("Black")){
			System.out.println("OthelloGameLogic::testSetPieceTrue has been successful");
		}else{
			System.out.println("OthelloGameLogic::testSetPieceTrue has not been successful");
		}

		testPlayer.setColour("Black");

		Piece testPiece = new Piece("White");

		/*
		 * Set the top piece of the board to White. This is a quasi-hack because
		 * now the top piece is occupied whilst everyone below it isn't and that
		 * wouldn't happen in the running implementation but it suffices here
		 * for test purposes.
		 *
		 */


		othelloGameLogic.getBoard().setPiece(testPiece, 0, 0);

		othelloGameLogic.setPiece(0, 0, testPlayer);

		/*
		 * If top piece is still White then setPiece did nothing which is correct.
		 * Basically our implementation tests if a move is valid before setting a piece
		 * and so won't set a piece if it's not valid anyway but we wanted to include
		 * a test to show this.
		 *
		 */


		if(othelloGameLogic.getBoard().getBoard()[0][0].getColour().equals("White")){
			System.out.println("OthelloGameLogic::testSetPieceFalse has been successful");
		}else{
			System.out.println("OthelloGameLogic::testSetPieceFalse has not been successful");
		}



		try{

			othelloGameLogic.setPiece(TOO_BIG, TOO_BIG, testPlayer);

		}catch(Exception e){
			System.out.println("OthelloGameLogic::testSetPiecesTooBig()");
		}

		//try to set pieces that are too small
		try{

			othelloGameLogic.setPiece(TOO_SMALL, TOO_SMALL, testPlayer);

		} catch (Exception e){
			System.out.println("OthelloGameLogic::testSetPieceTooSmall()");
		}

		try{

			othelloGameLogic.checkValid(0, 0, testPlayer);

		} catch(Exception e){
			System.out.println("OthelloGameLogic::testMoveIsValidTrue()");
		}

		othelloGameLogic.getBoard().setPiece(testPiece, 0, 0);

		if((othelloGameLogic.checkValid(0, 0, testPlayer)==(false))){
			System.out.println("OthelloGameLogic::testMoveIsValidFalse()");
		}

		try{
			othelloGameLogic.checkValid(TOO_BIG, TOO_BIG, testPlayer);
		}catch(Exception e){
			System.out.println("OthelloGameLogic::testMoveIsValidTooBig(");
		}

		try{
			othelloGameLogic.checkValid(TOO_SMALL, TOO_SMALL, testPlayer);
		}catch(Exception e){
			System.out.println("OthelloGameLogic::testMoveIsValidTooSmall()");
		}


		Piece testpiece = new Piece("Black");

		if(othelloGameLogic.checkWin()==true){
			othelloGameLogic.getBoard().setPiece(testPiece, 0, 6);
			othelloGameLogic.getBoard().setPiece(testPiece, 0, 5);
			othelloGameLogic.getBoard().setPiece(testPiece, 0, 4);
			othelloGameLogic.getBoard().setPiece(testPiece, 0, 3);
			System.out.println("OthelloGameLogic::checkWinVertical() succeeded");
		}else{
			System.out.println("OthelloGameLogic::checkWinVertical() failed");
		}

		if(othelloGameLogic.checkWin()==true){
			othelloGameLogic.getBoard().setPiece(testPiece, 0, 6);
			othelloGameLogic.getBoard().setPiece(testPiece, 1, 6);
			othelloGameLogic.getBoard().setPiece(testPiece, 2, 6);
			othelloGameLogic.getBoard().setPiece(testPiece, 3, 6);
			System.out.println("OthelloGameLogic::checkWinHorizontal() succeeded");
		}else{
			System.out.println("OthelloGameLogic::checkWinHorizontal() failed");
		}

		if(othelloGameLogic.checkWin()==true){
			othelloGameLogic.getBoard().setPiece(testPiece, 0, 3);
			othelloGameLogic.getBoard().setPiece(testPiece, 1, 4);
			othelloGameLogic.getBoard().setPiece(testPiece, 1, 4);
			othelloGameLogic.getBoard().setPiece(testPiece, 1, 4);
			System.out.println("OthelloGameLogic::checkWinRightDiagonal succeeded");
		}else{
			System.out.println("OthelloGameLogic::checkWinRightDiagonal failed");
		}

		if(othelloGameLogic.checkWin()==true){
			othelloGameLogic.getBoard().setPiece(testPiece, 9, 3);
			othelloGameLogic.getBoard().setPiece(testPiece, 8, 4);
			othelloGameLogic.getBoard().setPiece(testPiece, 7, 5);
			othelloGameLogic.getBoard().setPiece(testPiece, 6, 6);
			System.out.println("OthelloGameLogic::checkWinLefttDiagonal succeeded");
		}else{
			System.out.println("OthelloGameLogic::checkWinLeftDiagonal failed");
		}

		if(othelloGameLogic.checkWin()==false){
			System.out.println("othelloGameLogic::checkWinFalse() has succeeded");
		}else{
			System.out.println("othelloGameLogic::checkWinFalse() has failed");
		}

		int x;
		Piece testPiece1 = new Piece("Black");
		Piece testPiece2 = new Piece("White");

		for(x = 0; x < othelloGameLogic.getBoard().getBoardWidth(); x = x + 4){
			for(int y = 0; y < othelloGameLogic.getBoard().getBoardHeight(); y++){
				if((y % 2) == 0){
					othelloGameLogic.getBoard().setPiece(testPiece1, x, y);
					othelloGameLogic.getBoard().setPiece(testPiece1, x + 1, y);
				}else{
					othelloGameLogic.getBoard().setPiece(testPiece2, x, y);
					othelloGameLogic.getBoard().setPiece(testPiece2, x + 1, y);
				}
			}
		}

		for(x = 2; x < othelloGameLogic.getBoard().getBoardWidth(); x = x + 4){
			for(int y = 0; y < othelloGameLogic.getBoard().getBoardHeight(); y++){
				if((y % 2) == 0){
					othelloGameLogic.getBoard().setPiece(testPiece2, x, y);
					othelloGameLogic.getBoard().setPiece(testPiece2, x + 1, y);
				}else{
					othelloGameLogic.getBoard().setPiece(testPiece1, x, y);
					othelloGameLogic.getBoard().setPiece(testPiece1, x + 1, y);
				}
			}
		}

		if(othelloGameLogic.checkWin()==false){
			System.out.println("OthelloGameLogic::checkWinDraw() succeeded");
		}else{
			System.out.println("OthelloGameLogic::checkWinDraw() failed");
		}



  }

	/** Symbolic Constants */

    private final int PLAYER_ONE_WIN = 1;
    private final int PLAYER_TWO_WIN = 2;
    private final int CONTINUE_GAME = -1;
    private final int DRAW = 3;

	private final int NUMBER_OF_PLAYERS = 2;
    private final int DIVIDED_BY_TWO = 2;
	private final int BOARD_HEIGHT = 8;
	private final int BOARD_WIDTH = 8;
	private final String BLACK_PIECE = "Black";
	private final String WHITE_PIECE = "White";
}